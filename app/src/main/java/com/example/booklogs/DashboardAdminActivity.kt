package com.example.booklogs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booklogs.adapter.bookAdapter
import com.example.booklogs.databinding.ActivityDashboardAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DashboardAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardAdminBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recyclerview : RecyclerView
    private lateinit var reference : DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var bookarrayList : ArrayList<books>
    private lateinit var searchList : ArrayList<books>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDashboardAdminBinding.inflate(layoutInflater)
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
            checkUser()
        }
        binding.addCategoryButton.setOnClickListener {
            startActivity(Intent(this, CategoryAddActivity::class.java))
//            finish()
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser==null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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
                            val intent = Intent(this@DashboardAdminActivity, Bookdetails::class.java)
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