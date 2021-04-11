package com.example.yaroslavgorbach.randomizer.screen.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yaroslavgorbach.randomizer.R

class ListTitlesAdapter(onItemClick: (String) -> Unit,
                        onEditClick: (String) -> Unit,
                        onDeleteClick: (String) -> Unit) : ListAdapter<String, ListTitlesAdapter.Vh>(DiffCallback()) {
    private val mItemCLickListener = onItemClick
    private val mEditIconClickListener = onEditClick
    private val mOnDeleteCLickListener = onDeleteClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(parent, onItemClick = mItemCLickListener,
            onEditClick = mEditIconClickListener,
            onDeleteClick = mOnDeleteCLickListener)
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

    class Vh(parent: ViewGroup, onItemClick: (String) -> Unit,
             onEditClick: (String) -> Unit,
             onDeleteClick: (String) -> Unit) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.llist_rv_title, parent, false)) {
        private val listTitleTv: TextView = itemView.findViewById(R.id.itemTitle)
        private val editIcon: ImageButton = itemView.findViewById(R.id.edit)
        private val deleteIcon: ImageButton = itemView.findViewById(R.id.delete)

        init {
            itemView.setOnClickListener {
                onItemClick(listTitleTv.text.toString())
            }
            editIcon.setOnClickListener {
                onEditClick(listTitleTv.text.toString())
            }
            deleteIcon.setOnClickListener {
                onDeleteClick(listTitleTv.text.toString())
            }
        }
        fun bindTo(item: String?) {
            listTitleTv.text = item
        }

    }

}