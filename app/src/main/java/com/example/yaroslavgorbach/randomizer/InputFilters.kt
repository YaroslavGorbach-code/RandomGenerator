package com.example.yaroslavgorbach.randomizer

import com.google.android.material.textfield.TextInputEditText

class InputFilters {
    companion object{
        fun diceFilter(et: TextInputEditText): Boolean{
            if (et.text.toString().trim().isEmpty()
                    || Integer.valueOf(et.text.toString()) < 1
                    || Integer.valueOf(et.text.toString()) > 50){
                et.setError("The value must be between 1 and 50" )
                et.setText("1")
                return false
            }
            return true
        }
    }

}