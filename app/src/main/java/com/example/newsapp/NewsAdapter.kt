package com.example.newsapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter( private val listner:NewsItemClicked): RecyclerView.Adapter<newsViewHolder>() {
    private val items:ArrayList<newsData> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.items_views,parent,false)
        val  viewHolder= newsViewHolder(view)
        view.setOnClickListener{
            listner.itemclicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: newsViewHolder, position: Int) {
        val currentItem=items[position]
        holder.title.text=currentItem.title
        holder.author.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.urlImg).into(holder.image)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItem(updatedItem:ArrayList<newsData>){
        items.clear()
        items.addAll(updatedItem)

        notifyDataSetChanged()
    }

}
class newsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var title:TextView= itemView.findViewById(R.id.Title)
    var image:ImageView=itemView.findViewById(R.id.Image)
    var author:TextView=itemView.findViewById(R.id.Author)
}
interface NewsItemClicked{
    fun itemclicked(items: newsData)
}