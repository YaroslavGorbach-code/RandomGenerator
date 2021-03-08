package com.example.yaroslavgorbach.randomizer.dice

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.yaroslavgorbach.randomizer.R

class DicesAdapter(private val onClick: (ImageView) -> Unit): RecyclerView.Adapter<DicesAdapter.Vh>() {
    private var mNumberOfDices: Int = 0

    fun setNumberOfDices(number: Int){
        mNumberOfDices = number
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
       return Vh(parent, this.onClick)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(position, mNumberOfDices)
    }

    override fun getItemCount(): Int {
        return mNumberOfDices
    }

    class Vh(parent: ViewGroup, onClick: (ImageView) -> Unit): RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.dice_i, parent, false)){
        private val diceImage: ImageView = itemView.findViewById(R.id.dice_i)

        init {
            diceImage.setOnClickListener{
                onClick(diceImage)
            }
        }

        fun bind(position: Int, number: Int) {
            diceImage.apply {
                when(position){
                    0,6,12,18,24 -> setImageResource(R.drawable.ic_dice_1)
                    1,7,13,19,25 -> setImageResource(R.drawable.ic_dice_2)
                    2,8,14,20,26 -> setImageResource(R.drawable.ic_dice_3)
                    3,9,15,21,27 -> setImageResource(R.drawable.ic_dice_4)
                    4,10,16,22,28 -> setImageResource(R.drawable.ic_dice_5)
                    5,11,17,23,29 -> setImageResource(R.drawable.ic_dice_6)
                }
            }
        }
    }
}