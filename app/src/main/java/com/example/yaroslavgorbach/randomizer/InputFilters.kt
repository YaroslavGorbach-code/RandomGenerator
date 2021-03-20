package com.example.yaroslavgorbach.randomizer

import com.google.android.material.textfield.TextInputEditText

class InputFilters {
    companion object {
        fun diceFilter(et: TextInputEditText): Boolean {
            if (et.text.toString().trim().isEmpty()
                || Integer.valueOf(et.text.toString()) < 1
                || Integer.valueOf(et.text.toString()) > 50
            ) {
                et.error = "Value must be between 1 and 50"
                et.setText("1")
                return false
            }
            return true
        }

        fun numberMaxMinFilter(et: TextInputEditText): Boolean {
            if (et.text.toString().trim().isEmpty()
                || et.text.toString().toLong() < Long.MIN_VALUE
                || et.text.toString().toLong() > Long.MAX_VALUE
            ) {
                et.error = "Incorrect value"
                return false
            }
            return true
        }

        fun numberMinBiggerThenMaxFilter(min: TextInputEditText, max: TextInputEditText): Boolean {
            if (min.text.toString().toLong() > max.text.toString().toLong()) {
                min.error = "Min value cannot be more then max"
                return false
            }
            return true
        }

        fun numberOfResultsFilter(et: TextInputEditText): Boolean {
            if (et.text.toString().trim().isEmpty()
                || et.text.toString().toLong() > 10
                || et.text.toString().toLong() < 1
            ) {
                et.error = "Value cannot be less than 1 and more than 10"
                return false
            }
            return true
        }

        fun matchesFieldsFilter(
            numberOfMatchesEt: TextInputEditText,
            numberOfBurnedEt: TextInputEditText
        ): Boolean {
            if (numberOfMatchesEt.text.toString().trim().isEmpty()
                || numberOfMatchesEt.text.toString().toInt() < 1) {
                numberOfMatchesEt.error = "This field cannot be empty and les then 1"
                numberOfMatchesEt.setText("5")
                return false
            }

            if (numberOfBurnedEt.text.toString().trim().isEmpty()
                || numberOfBurnedEt.text.toString().toInt() < 0) {
                numberOfBurnedEt.error = "This field cannot be empty and les then 0"
                numberOfBurnedEt.setText("1")
                return false
            }

            if (numberOfMatchesEt.text.toString().toInt() > 500) {
                numberOfMatchesEt.error = "Value cannot be bigger then 500"
                return false
            }

            if (numberOfBurnedEt.text.toString().toInt() > 500) {
                numberOfBurnedEt.error = "Value cannot be bigger then 500"
                return false
            }

            if (numberOfMatchesEt.text.toString().toInt() < numberOfBurnedEt.text.toString()
                    .toInt()
            ) {
                numberOfBurnedEt.error = "Value cannot be bigger then number of matches"
                return false
            }
            return true
        }

        fun createListDialogTitleFilter(listTitleEt: TextInputEditText?): Boolean {
            if (listTitleEt?.text.toString().trim().isEmpty()){
                listTitleEt?.error = "Title cannot be empty"
                return false
            }
            return true
        }

        fun createListItemTestFilter(itemTextEt: TextInputEditText): Boolean {
            if (itemTextEt.text.toString().trim().isEmpty()){
                itemTextEt.error = "Item cannot be empty"
                return false
            }
            return true
        }
    }

}