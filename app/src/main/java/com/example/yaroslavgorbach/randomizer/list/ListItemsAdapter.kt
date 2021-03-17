package com.example.yaroslavgorbach.randomizer.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yaroslavgorbach.randomizer.R

class ListItemsAdapter : ListAdapter<String, ListItemsAdapter.Vh>(DiffCallback()) {
    private var mOnItemDeleteCLick: ((Int) -> Unit)? = null

    fun addDeleteListener(onItemDeleteCLick:(Int) -> Unit){
        mOnItemDeleteCLick = onItemDeleteCLick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(parent, mOnItemDeleteCLick)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bindTo(getItem(position))
    }

    class Vh(parent: ViewGroup, onItemDeleteCLick: ((Int) -> Unit)?) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.llist_rv_item, parent, false)) {
        var listTitleTv: TextView = itemView.findViewById(R.id.itemTitle)
        var deleteItem: ImageButton = itemView.findViewById(R.id.delete)
        init {
            deleteItem.setOnClickListener {
                onItemDeleteCLick?.let { onItemDeleteCLick(adapterPosition) }
            }
        }
        fun bindTo(item: String?) {
            listTitleTv.text = item
        }
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