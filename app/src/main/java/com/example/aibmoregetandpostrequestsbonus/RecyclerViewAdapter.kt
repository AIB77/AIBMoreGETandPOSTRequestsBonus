package com.example.aibmoregetandpostrequestsbonus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter (val context: Context, val userList : List<MyDataItem>): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> (){
    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        var username: TextView

        init {
            username= itemView.tvResult
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return  ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.username.text=userList[position].name.toString()
    }

    override fun getItemCount() = userList.size

}
