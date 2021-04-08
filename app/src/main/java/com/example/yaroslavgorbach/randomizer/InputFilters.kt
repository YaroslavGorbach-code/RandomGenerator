package com.example.yaroslavgorbach.randomizer

import com.google.android.material.textfield.TextInputEditText

class InputFilters {
    companion object {
        fun diceFilter(et: TextInputEditText): Boolean {
            if (et.text.toString().trim().isEmpty()
                || Integer.valueOf(et.text.toString()) < 1
                || Integer.valueOf(et.text.toString()) > 50
            ) {
                et.error = et.context.getString(R.string.dice_value)
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
                et.error = et.context.getString(R.string.number_max_min)
                return false
            }
            return true
        }

        fun numberMinBiggerThenMaxFilter(min: TextInputEditText, max: TextInputEditText): Boolean {
            if (min.text.toString().toLong() > max.text.toString().toLong()) {
                min.error = min.context.getString(R.string.number_min_bigger_then_max)
                return false
            }
            return true
        }

        fun numberOfResultsFilter(et: TextInputEditText): Boolean {
            if (et.text.toString().trim().isEmpty()
                || et.text.toString().toLong() > 100
                || et.text.toString().toLong() < 1
            ) {
                et.error = et.context.getString(R.string.number_results_too_large)
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
                numberOfMatchesEt.error = numberOfBurnedEt.context.getString(R.string.matches_empty)
                numberOfMatchesEt.setText("5")
                return false
            }

            if (numberOfBurnedEt.text.toString().trim().isEmpty()
                || numberOfBurnedEt.text.toString().toInt() < 0) {
                numberOfBurnedEt.error = numberOfBurnedEt.context.getString(R.string.number_of_burned_invalid)
                numberOfBurnedEt.setText("1")
                return false
            }

            if (numberOfMatchesEt.text.toString().toInt() > 500) {
                numberOfMatchesEt.error = numberOfBurnedEt.context.getString(R.string.number_of_matches_too_large)
                return false
            }

            if (numberOfBurnedEt.text.toString().toInt() > 500) {
                numberOfBurnedEt.error = numberOfBurnedEt.context.getString(R.string.number_of_burned_too_large)
                return false
            }

            if (numberOfMatchesEt.text.toString().toInt() < numberOfBurnedEt.text.toString().toInt()) {
                numberOfBurnedEt.error = numberOfBurnedEt.context.getString(R.string.number_of_burned_too_large)
                return false
            }
            return true
        }

        fun createListDialogTitleFilter(listTitleEt: TextInputEditText): Boolean {
            if (listTitleEt.text.toString().trim().isEmpty()){
                listTitleEt.error = listTitleEt.context.getString(R.string.dialog_title_empty)
                return false
            }
            return true
        }

        fun createListItemTestFilter(itemTextEt: TextInputEditText): Boolean {
            if (itemTextEt.text.toString().trim().isEmpty()){
                itemTextEt.error = itemTextEt.context.getString(R.string.item_empty)
                return false
            }
            return true
        }
    }

}