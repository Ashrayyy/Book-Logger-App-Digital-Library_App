package com.example.booklogs

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.booklogs.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        firebaseAuth= FirebaseAuth.getInstance()

        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.noAccountTv.setOnClickListener{
            startActivity(Intent( this,RegisterActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            validateData()
        }

    }

    private var email=""
    private var password=""
    private fun validateData() {
        email=binding.emailEt.text.toString().trim()
        password=binding.PasswordEt.text.toString().trim()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Please Enter Valid Email",Toast.LENGTH_SHORT).show()
        }
        else if(password.isEmpty()){
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show()
        }
        else{
            loginUser()
        }
    }

    private fun loginUser() {
        progressDialog.setMessage("Logging in, Please Wait")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                checUser()
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed, reason: ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun checUser() {
        progressDialog.setMessage("Checking User")
        val firebaseUser=firebaseAuth.currentUser!!
        val ref=FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    progressDialog.dismiss()
                    val userType=snapshot.child("userType").value
                    if(userType=="user"){
                        startActivity(Intent(this@LoginActivity,DashboardUserActivity::class.java))
                        finish()
                    }
                    else if(userType=="admin"){
                        startActivity(Intent(this@LoginActivity,DashboardAdminActivity::class.java))
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}