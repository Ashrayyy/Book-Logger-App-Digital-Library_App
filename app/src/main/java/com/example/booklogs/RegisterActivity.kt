package com.example.booklogs

import android.app.ProgressDialog
import android.content.Intent
import android.media.tv.TvContract.Programs
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.booklogs.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val view=binding.root
        setContentView(view)

        firebaseAuth= FirebaseAuth.getInstance()

        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.backButton.setOnClickListener{
            onBackPressed()
        }
        binding.registerButton.setOnClickListener {
            validateData()
        }
    }
    private var name=""
    private var email=""
    private var password=""

    private fun validateData() {
        name=binding.nameEt.text.toString().trim()
        email=binding.emailEt.text.toString().trim()
        password=binding.PasswordEt.text.toString().trim()
        val cPassword=binding.cPasswordEt.text.toString().trim()
        if(name.isEmpty()){
            Toast.makeText(this,"Please Enter Name",Toast.LENGTH_SHORT).show()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Please Enter a valid Email",Toast.LENGTH_SHORT).show()
        }
        else if(password.isEmpty()){
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show()
        }
        else if(cPassword.isEmpty()){
            Toast.makeText(this,"Please Confirm Password",Toast.LENGTH_SHORT).show()
        }
        else if(password!=cPassword){
            Toast.makeText(this,"Password doesn't match",Toast.LENGTH_SHORT).show()
        }
        else{
            createUserAccount()
        }
    }

    private fun createUserAccount() {
        progressDialog.setMessage("Creating Account, Please Wait")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                updateUserInfo()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failes to create Acccount due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserInfo() {
        progressDialog.setMessage("Saving Details")
        val timestamp=System.currentTimeMillis()

        val uid=firebaseAuth.uid
        val hashMap: HashMap<String,Any?> = HashMap()
        hashMap["uid"]=uid
        hashMap["email"]=email
        hashMap["name"]=name
        hashMap["profileImage"]=""
        hashMap["userType"]="user"
        hashMap["timeStamp"]=timestamp

        val ref=FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Account Has Been Created", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity,DashboardUserActivity::class.java    ))
                finish()
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failes saving user details due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }
}