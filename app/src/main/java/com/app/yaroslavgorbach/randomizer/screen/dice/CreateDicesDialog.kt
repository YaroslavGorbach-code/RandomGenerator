package com.app.yaroslavgorbach.randomizer.screen.dice

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.app.yaroslavgorbach.randomizer.InputFilters
import com.app.yaroslavgorbach.randomizer.databinding.DialogCreateDicesBinding
import com.app.yaroslavgorbach.randomizer.screen.nav
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CreateDicesDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding  = DialogCreateDicesBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
        dialog.window?.showKeyBoard()

        binding.create.setOnClickListener {
            if (InputFilters.diceFilter(binding.numberDices)){
                nav.openDice(binding.numberDices.toInt())
                dialog.dismiss()
            }
        }
        return dialog
    }
}