package com.example.yaroslavgorbach.randomizer.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yaroslavgorbach.randomizer.R

class ListTitlesAdapter(onItemClick: (String) -> Unit) : ListAdapter<String, ListTitlesAdapter.Vh>(DiffCallback()) {
    private val mCLickListener = onItemClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(parent, onItemClick = mCLickListener)
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

    class Vh(parent: ViewGroup, onItemClick: (String) -> Unit) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.llist_rv_title, parent, false)) {
        private var listTitleTv: TextView = itemView.findViewById(R.id.itemTitle)

        init {
            itemView.setOnClickListener {
                onItemClick(listTitleTv.text.toString())
            }
        }
        fun bindTo(item: String?) {
            listTitleTv.text = item
        }

    }

}