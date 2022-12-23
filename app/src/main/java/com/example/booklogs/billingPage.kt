package com.example.booklogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Billingpage : AppCompatActivity() {

    private lateinit var tvbookName1 : TextView
    private lateinit var tvbookauth1 : TextView
    private lateinit var tvbookOverview1 : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billing_page)

        tvbookName1 = findViewById(R.id.tvbookname1)
        tvbookauth1 = findViewById(R.id.tvbokauth1)
        tvbookOverview1 = findViewById(R.id.tvbookoverview1)

        setvalues()
    }


    private fun setvalues(){
        tvbookName1.text = intent.getStringExtra("bookname")
        tvbookauth1.text = intent.getStringExtra("bookauth")
        tvbookOverview1.text = intent.getStringExtra("bookoverview")
    }
}