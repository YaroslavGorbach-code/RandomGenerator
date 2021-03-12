package com.example.yaroslavgorbach.randomizer

import com.google.android.material.textfield.TextInputEditText

class InputFilters {
    companion object{
        fun diceFilter(et: TextInputEditText): Boolean{
            if (et.text.toString().trim().isEmpty()
                    || Integer.valueOf(et.text.toString()) < 1
                    || Integer.valueOf(et.text.toString()) > 50){
                et.error = "The value must be between 1 and 50"
                et.setText("1")
                return false
            }
            return true
        }

        fun numberMaxMinFilter(et: TextInputEditText): Boolean{
            if (et.text.toString().trim().isEmpty()
                || et.text.toString().toLong() < Long.MIN_VALUE
                || et.text.toString().toLong() > Long.MAX_VALUE){
                et.error = "Incorrect value"
                return false
            }
            return true
        }

        fun numberMinBiggerThenMaxFilter(min: TextInputEditText, max: TextInputEditText): Boolean{
            if (min.text.toString().toLong() > max.text.toString().toLong()){
                min.error = "Min value can not be bigger then max"
                return false
            }
            return true
        }
    }

}