package com.example.yaroslavgorbach.randomizer.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yaroslavgorbach.randomizer.R

class ListTitlesAdapter : ListAdapter<String, ListTitlesAdapter.Vh>(DiffCallback()) {

    class Vh(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.llist_rv_title, parent, false)) {
        var listTitleTv: TextView = itemView.findViewById(R.id.itemTitle)
        fun bindTo(item: String?) {
            listTitleTv.text = item
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(parent)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bindTo(getItem(position))
    }

        class DiffCallback : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }

}