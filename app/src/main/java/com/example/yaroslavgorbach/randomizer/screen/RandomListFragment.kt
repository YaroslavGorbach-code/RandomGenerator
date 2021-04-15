package com.example.yaroslavgorbach.randomizer.screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yaroslavgorbach.randomizer.InputFilters
import com.example.yaroslavgorbach.randomizer.MyApplication
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.screen.list.CreateEditListDialog
import com.example.yaroslavgorbach.randomizer.data.database.Repo
import com.example.yaroslavgorbach.randomizer.screen.list.ListTitlesAdapter
import com.example.yaroslavgorbach.randomizer.data.themePref.ThemeStorageImp
import com.example.yaroslavgorbach.randomizer.util.ThemesUtils
import com.example.yaroslavgorbach.randomizer.feature.colorPicker.ColorPicker
import com.example.yaroslavgorbach.randomizer.feature.colorPicker.ScrollColorPicker
import com.example.yaroslavgorbach.randomizer.data.themePref.ThemeStorage
import com.example.yaroslavgorbach.randomizer.util.toInt
import com.example.yaroslavgorbach.randomizer.util.toLong
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RandomListFragment : Fragment(R.layout.fragment_random_list) {
    private lateinit var mStartDice: MaterialCardView
    private lateinit var mStartCoin: MaterialCardView
    private lateinit var mStartNumber: MaterialCardView
    private lateinit var mStartList: MaterialCardView
    private lateinit var mStartMatches: MaterialCardView
    private lateinit var mToolbar: Toolbar

    @Inject lateinit var mRepo: Repo

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApplication).appComponent.inject(this)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mStartDice = view.findViewById(R.id.startDice)
        mStartCoin = view.findViewById(R.id.startCoin)
        mStartNumber = view.findViewById(R.id.startNumber)
        mStartList = view.findViewById(R.id.startList)
        mStartMatches = view.findViewById(R.id.startMatches)
        mToolbar = view.findViewById(R.id.mainToolbar)

        mToolbar.setOnMenuItemClickListener { item->
            when(item.itemId){
                R.id.changeTheme -> {
                    val themeStorage: ThemeStorage = ThemeStorageImp(requireContext())
                    val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_color_picker, null)
                    val colorPicker: ColorPicker = ScrollColorPicker(dialogView.findViewById(R.id.colors))
                    val nightModCb: CheckBox = dialogView.findViewById(R.id.night_mode)
                    nightModCb.isChecked = themeStorage.getNightMode()
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Chose theme")
                        .setView(dialogView)
                        .setPositiveButton("Apply") { _, _ ->
                            themeStorage.changeTheme(colorPicker.getColor(requireContext()))
                            themeStorage.changeNightMode(nightModCb.isChecked)
                            ThemesUtils.animateThemeChange(requireActivity())
                        }
                        .show()

                }
                R.id.rate -> {
                }
                R.id.share -> {
                }
                R.id.removeAd -> {
                }
            }
            true
        }

        mStartDice.setOnClickListener {
            val dialogView: View = LayoutInflater.from(context).inflate(
                R.layout.create_dices_dialog,
                null
            )
            val numberOfDiceEt = dialogView.findViewById<TextInputEditText>(R.id.maxValue)
            val positiveButton = dialogView.findViewById<MaterialButton>(R.id.createButton)
            val dialog = MaterialAlertDialogBuilder(requireContext())
                    .setView(dialogView)
                    .show()
            positiveButton.setOnClickListener {
                        if (InputFilters.diceFilter(numberOfDiceEt)){
                            nav.openDice(numberOfDiceEt.toInt())
                            dialog.dismiss()
                        }
            }
        }
        mStartCoin.setOnClickListener{
            nav.openCoin()
        }

        mStartNumber.setOnClickListener{
            val dialogView: View = LayoutInflater.from(context).inflate(
                R.layout.create_number_dialog,
                null
            )
            val maxValue = dialogView.findViewById<TextInputEditText>(R.id.maxValue)
            val minValue = dialogView.findViewById<TextInputEditText>(R.id.minValue)
            val numberOfResult = dialogView.findViewById<TextInputEditText>(R.id.numberOfResults)

            val positiveButton = dialogView.findViewById<MaterialButton>(R.id.createButton)
            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setView(dialogView)
                .show()
            positiveButton.setOnClickListener {
                if (InputFilters.numberMaxMinFilter(maxValue)
                        && InputFilters.numberMaxMinFilter(minValue)
                        && InputFilters.numberMinBiggerThenMaxFilter(minValue, maxValue)
                        && InputFilters.numberOfResultsFilter(numberOfResult)
                ){
                    nav.openNumber(maxValue.toLong(), minValue.toLong(), numberOfResult.toLong())
                    dialog.dismiss()
                }
            }
        }

        mStartList.setOnClickListener{
            val dialogView: View = LayoutInflater.from(context).inflate(
                R.layout.chose_list_dialog,
                null
            )
            val createListButton = dialogView.findViewById<MaterialButton>(R.id.createButton)
            val rvList: RecyclerView = dialogView.findViewById(R.id.recyclerView)
            val listDialog = MaterialAlertDialogBuilder(requireContext())
                    .setView(dialogView)
                    .show()

            val titleAdapter = ListTitlesAdapter(onItemClick = {
                nav.openList(it)
                listDialog.dismiss()
            }, onEditClick = {
                CreateEditListDialog.newInstance(title = it)
                    .show(childFragmentManager, "createEditDialog")

            }, onDeleteClick = {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Delete list?")
                    .setMessage("This action cannot be undone") // TODO: 4/11/2021 translate
                    .setPositiveButton("yes") { _, _ ->
                        GlobalScope.launch {
                            mRepo.deleteItemsByTitle(title = it)
                        }
                    }
                    .setNegativeButton("cancel") { _, _ -> }
                    .show()
            })

            mRepo.getTitles().observe(viewLifecycleOwner, {
                titleAdapter.submitList(it)
            })

            rvList.apply {
                adapter = titleAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }

            createListButton.setOnClickListener {
                CreateEditListDialog.newInstance(title = null)
                    .show(parentFragmentManager, "createEditDialog")
            }
        }

        mStartMatches.setOnClickListener {
            val dialogView: View = LayoutInflater.from(context).inflate(
                R.layout.create_matches_dialog,
                null
            )
            val numberOfMatchesEt = dialogView.findViewById<TextInputEditText>(R.id.number_matches)
            val numberOfBurnedEt = dialogView.findViewById<TextInputEditText>(R.id.number_burned)
            val createListButton = dialogView.findViewById<MaterialButton>(R.id.createButton)
            val dialog = MaterialAlertDialogBuilder(view.context)
                .setView(dialogView)
                .show()

            createListButton.setOnClickListener {
                if(InputFilters.matchesFieldsFilter(numberOfMatchesEt, numberOfBurnedEt)){
                        nav.openMatches(numberOfMatchesEt.toInt(), numberOfBurnedEt.toInt())
                        dialog.dismiss()
                }
            }
        }
    }
}
