package com.example.yaroslavgorbach.randomizer.screen.dice

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.yaroslavgorbach.randomizer.InputFilters
import com.example.yaroslavgorbach.randomizer.databinding.DialogCreateDicesBinding
import com.example.yaroslavgorbach.randomizer.screen.nav
import com.example.yaroslavgorbach.randomizer.util.toInt
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CreateDicesDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding  = DialogCreateDicesBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()

        binding.create.setOnClickListener {
            if (InputFilters.diceFilter(binding.numberDices)){
                nav.openDice(binding.numberDices.toInt())
                dialog.dismiss()
            }
        }
        return dialog
    }
}