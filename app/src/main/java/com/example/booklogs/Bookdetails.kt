package com.example.booklogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Bookdetails : AppCompatActivity() {

    private lateinit var tvbookName : TextView
    private lateinit var tvbookauth : TextView
    private lateinit var tvbookOverview : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookdetails)

        tvbookName = findViewById(R.id.tvbookname)
        tvbookauth = findViewById(R.id.tvbokauth)
        tvbookOverview = findViewById(R.id.tvbookoverview)

        setvalues()
    }


    private fun setvalues(){
        tvbookName.text = intent.getStringExtra("bookname")
        tvbookauth.text = intent.getStringExtra("bookauth")
        tvbookOverview.text = intent.getStringExtra("bookoverview")
    }
}