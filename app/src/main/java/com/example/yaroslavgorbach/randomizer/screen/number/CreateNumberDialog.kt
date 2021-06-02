package com.example.yaroslavgorbach.randomizer.screen.number

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.yaroslavgorbach.randomizer.InputFilters
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.databinding.DialogCreateNumberBinding
import com.example.yaroslavgorbach.randomizer.screen.nav
import com.example.yaroslavgorbach.randomizer.util.showKeyBoard
import com.example.yaroslavgorbach.randomizer.util.toLong
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CreateNumberDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogCreateNumberBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
        dialog.window?.showKeyBoard()
        binding.generate.setOnClickListener {
            if (InputFilters.numberMaxMinFilter(binding.maxNumber)
                && InputFilters.numberMaxMinFilter(binding.minNumber)
                && InputFilters.numberMinBiggerThenMaxFilter(binding.minNumber, binding.maxNumber)
                && InputFilters.numberOfResultsFilter(binding.resultsNumber)
            ) {
                nav.openNumber(
                    binding.maxNumber.toLong(),
                    binding.minNumber.toLong(),
                    binding.resultsNumber.toLong()
                )
                dialog.dismiss()
            }
        }
        return dialog
    }
}