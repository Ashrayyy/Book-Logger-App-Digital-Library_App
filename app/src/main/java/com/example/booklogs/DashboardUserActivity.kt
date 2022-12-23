package com.example.booklogs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booklogs.adapter.bookAdapter
import com.example.booklogs.databinding.ActivityDashboardUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DashboardUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardUserBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recyclerview : RecyclerView
    private lateinit var reference : DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var bookarrayList : ArrayList<books>
    private lateinit var searchList : ArrayList<books>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDashboardUserBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        recyclerview = findViewById(R.id.rv)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this)
        bookarrayList = arrayListOf<books>()
        searchList = arrayListOf<books>()
        database = FirebaseDatabase.getInstance()


        firebaseAuth=FirebaseAuth.getInstance()
        checkUser()
        getbookData()

        binding.logoutButton.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.scanQrButton.setOnClickListener {
            checkUser1()
        }

    }

    private fun checkUser1() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser==null){
            Toast.makeText(this,"Login to use this Functionality", Toast.LENGTH_SHORT).show()
        }
        else{
            startActivity(Intent(this,qrCode::class.java))
//            finish()
        }
    }


    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser==null){
            binding.subtitleTv.text="Guest User"
        }
        else{
            val email=firebaseUser.email
            binding.subtitleTv.text=email
        }
    }
    private fun getbookData() {

        recyclerview.visibility = android.view.View.GONE
        // progressBar.setVisibility(View.VISIBLE)
        reference = database.getReference("books")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookarrayList.clear()

                if (snapshot.exists()) {
                    for (Snapshot in snapshot.children) {

                        val book = Snapshot.getValue(books::class.java)
                        bookarrayList.add(book!!)
                    }
                    searchList.addAll(bookarrayList)
                    val mAdapter = bookAdapter(bookarrayList)
                    recyclerview.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : bookAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@DashboardUserActivity, Bookdetails::class.java)
                            //put extra
                            intent.putExtra("bookname",bookarrayList[position].bookname)
                            intent.putExtra("bookoverview", bookarrayList[position].bookoverview)
                            intent.putExtra("bookauth", bookarrayList[position].bookauth)
                            startActivity(intent)
                        }

                    })


                    recyclerview.visibility = android.view.View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            })
       }
}