package com.example.yaroslavgorbach.randomizer.screen

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.data.themePref.ThemeStorage
import com.example.yaroslavgorbach.randomizer.data.themePref.ThemeStorageImp
import com.example.yaroslavgorbach.randomizer.databinding.DialogChangeThemeBinding
import com.example.yaroslavgorbach.randomizer.feature.colorPicker.ColorPicker
import com.example.yaroslavgorbach.randomizer.feature.colorPicker.ScrollColorPicker
import com.example.yaroslavgorbach.randomizer.util.ThemesUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ChangeThemeDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // TODO: 4/16/2021 inject it
        val binding = DialogChangeThemeBinding.inflate(LayoutInflater.from(requireContext()))
        val themeStorage: ThemeStorage = ThemeStorageImp(requireContext())
        val colorPicker: ColorPicker = ScrollColorPicker(binding.colorsParent)

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.chose_theme))
            .setView(binding.root)
            .setPositiveButton(getString(R.string.apply)) { _, _ ->
                themeStorage.changeTheme(colorPicker.getColor(requireContext()))
                ThemesUtils.animateThemeChange(requireActivity())
            }
            .show()
    }
}

