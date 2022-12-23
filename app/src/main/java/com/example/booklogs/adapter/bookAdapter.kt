package com.example.booklogs.adapter

import com.example.booklogs.books
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booklogs.R

class bookAdapter(private var bookLogs : ArrayList<books> ):RecyclerView.Adapter<bookAdapter.MyViewHolder>() {



        private lateinit var mlistener : onItemClickListener

    init {
        this.bookLogs = bookLogs
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mlistener = clickListener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_category, parent,false)
        return bookAdapter.MyViewHolder(itemView, mlistener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = bookLogs[position]
        holder.bookName.text = currentitem.bookname

    }

    override fun getItemCount(): Int {

        return bookLogs.size
    }

    class MyViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val bookName : TextView = itemView.findViewById(R.id.Bookrv)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}
   //  {
//}


//
//class MedAdapter(private var Medlist : ArrayList<medicines> ) :
//    RecyclerView.Adapter<MedAdapter.MyViewHolder>(){
//


//}

