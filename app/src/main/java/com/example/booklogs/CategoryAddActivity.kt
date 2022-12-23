package com.example.booklogs

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.booklogs.databinding.ActivityCategoryAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CategoryAddActivity : AppCompatActivity() {

    private lateinit var etbookname : EditText
    private lateinit var etauthor : EditText
    private lateinit var etoverview : EditText
    private lateinit var savedatabtn : Button
    private lateinit var dbref : DatabaseReference
//    private lateinit var backButton : Button



    private lateinit var binding: ActivityCategoryAddBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityCategoryAddBinding.inflate(layoutInflater)
//        val view=binding.root
        setContentView(R.layout.activity_category_add)




        etbookname = findViewById(R.id.book)
        etauthor = findViewById(R.id.author)
        etoverview = findViewById(R.id.overview)
        savedatabtn = findViewById(R.id.submitButton)
        dbref = FirebaseDatabase.getInstance().getReference("books")
//        backButton=findViewById(R.id.backButton)

        savedatabtn.setOnClickListener {
            savebookdata()
        }
//        backButton.setOnClickListener{
//            onBackPressed()
//        }

    }

    private fun savebookdata() {
        val bookname =  etbookname.text.toString()
        val author =  etauthor.text.toString()
        val overview = etoverview.text.toString()

        if(bookname.isEmpty()){
            etbookname.error = "Enter the Book name"
        }
        if(author.isEmpty()){
            etauthor.error = "Enter the author name"
        }
        if(overview.isEmpty()){
            etoverview.error = "Enter the Description"
        }

        val books = books(bookname,author,overview)

        dbref.child(bookname).setValue(books)
            .addOnCompleteListener {
                Toast.makeText(this,"Data Inserted Successfully",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }


//        firebaseAuth=FirebaseAuth.getInstance()
//
//        progressDialog=ProgressDialog(this)
//        progressDialog.setTitle("Please wait")
//        progressDialog.setCanceledOnTouchOutside(false)
//
//        binding.backButton.setOnClickListener{
//            onBackPressed()
//        }
//
//        binding.submitButton.setOnClickListener{
//            validateData()
//        }
//    }
//
//    private var category = ""
//
//    private fun validateData(){
//        category=binding.categoryEt.text.toString().trim()
//
//        if(category.isEmpty()){
//            Toast.makeText(this,"Enter Category",Toast.LENGTH_SHORT).show()
//        }
//        else{
//            addCategoryfirebase()
//        }
//    }
//
//    private fun addCategoryfirebase(){
//        progressDialog.show()
//
//        val timestamp = System.currentTimeMillis()
//
//        val hashMap = HashMap<String, Any>()
//        hashMap["id"] = "$timestamp"
//        hashMap["category"] = category
//        hashMap["timestamp"] = timestamp
//        hashMap["uid"]="${firebaseAuth.uid}"
//
//        val ref = FirebaseDatabase.getInstance().getReference("Categories")
//        ref.child("$timestamp")
//            .setValue(hashMap)
//            .addOnSuccessListener {
//                progressDialog.dismiss()
//                Toast.makeText(this,"Added Successfully",Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener{e->
//                progressDialog.dismiss()
//                Toast.makeText(this,"Failed to add due to ${e.message}",Toast.LENGTH_SHORT).show()
//            }
    }
}