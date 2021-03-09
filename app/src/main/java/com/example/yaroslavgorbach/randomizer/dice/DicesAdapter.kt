package com.example.yaroslavgorbach.randomizer.dice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.yaroslavgorbach.randomizer.R

class DicesAdapter(private val onClick: (ImageView, DiceModel) -> Unit): RecyclerView.Adapter<DicesAdapter.Vh>() {
    private var mDices: List<DiceModel> = arrayListOf()

    fun setData(dices: List<DiceModel>){
        mDices = dices
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
       return Vh(parent, mDices, this.onClick)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(mDices[position])
    }

    override fun getItemCount(): Int {
        return mDices.size
    }

    class Vh(parent: ViewGroup, list: List<DiceModel>, onClick: (ImageView, DiceModel) -> Unit): RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.dice_i, parent, false)){
        private val imageDice: ImageView = itemView.findViewById(R.id.dice_i)

        init {
            itemView.setOnClickListener{
                onClick(imageDice, list[adapterPosition])
            }
        }

        fun bind(model: DiceModel) {
             imageDice.setImageResource(model.image)
        }
    }
}