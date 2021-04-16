package com.example.yaroslavgorbach.randomizer.screen.matches
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.yaroslavgorbach.randomizer.InputFilters
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.databinding.DialogCreateMatchesBinding
import com.example.yaroslavgorbach.randomizer.screen.nav
import com.example.yaroslavgorbach.randomizer.util.toInt
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CreateMatchesDialog: DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogCreateMatchesBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
        binding.createMatches.setOnClickListener {
            if(InputFilters.matchesFieldsFilter(binding.numberMatches, binding.numberBurned)){
                nav.openMatches(binding.numberMatches.toInt(), binding.numberBurned.toInt())
                dialog.dismiss()
            }
        }
        return dialog
    }
}