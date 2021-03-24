package com.example.yaroslavgorbach.randomizer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yaroslavgorbach.randomizer.list.Database.Database
import com.example.yaroslavgorbach.randomizer.list.Database.ListItemEntity
import com.example.yaroslavgorbach.randomizer.list.Database.Repo
import com.example.yaroslavgorbach.randomizer.list.ListItemsAdapter
import com.example.yaroslavgorbach.randomizer.list.ListTitlesAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import javax.inject.Inject

class RandomListFragment : Fragment() {
    private lateinit var mStartDice: MaterialCardView
    private lateinit var mStartCoin: MaterialCardView
    private lateinit var mStartNumber: MaterialCardView
    private lateinit var mStartList: MaterialCardView
    private lateinit var mStartMatches: MaterialCardView
    @Inject lateinit var mRepo: Repo

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_random_list, container, false)
        mStartDice = view.findViewById(R.id.startDice)
        mStartCoin = view.findViewById(R.id.startCoin)
        mStartNumber = view.findViewById(R.id.startNumber)
        mStartList = view.findViewById(R.id.startList)
        mStartMatches = view.findViewById(R.id.startMatches)

        return view
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mStartDice.setOnClickListener {
            val dialogView: View = LayoutInflater.from(context).inflate(R.layout.create_dices_dialog,
                    null)
            val numberOfDiceEt = dialogView.findViewById<TextInputEditText>(R.id.maxValue)
            val positiveButton = dialogView.findViewById<MaterialButton>(R.id.createButton)
            val dialog = MaterialAlertDialogBuilder(requireContext())
                    .setView(dialogView)
                    .show()
            positiveButton.setOnClickListener {
                        if (InputFilters.diceFilter(numberOfDiceEt)){
                            findNavController().navigate(RandomListFragmentDirections.actionRandomListFragmentToDicesFragment()
                                    .setNumberOfDice(Integer.valueOf(numberOfDiceEt.text.toString())))
                            dialog.dismiss()
                        }
            }
        }
        mStartCoin.setOnClickListener{
            findNavController().navigate(RandomListFragmentDirections.actionRandomListFragmentToCoinFragment())
        }

        mStartNumber.setOnClickListener{
            val dialogView: View = LayoutInflater.from(context).inflate(R.layout.create_number_dialog,null)
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
                        && InputFilters.numberOfResultsFilter(numberOfResult)){
                            findNavController().navigate(RandomListFragmentDirections.actionRandomListFragmentToNumberFragment()
                            .setMaxValue(maxValue.text.toString().toLong())
                            .setMinValue(minValue.text.toString().toLong())
                            .setNumberOfResults(numberOfResult.text.toString().toInt()))
                    dialog.dismiss()
                }
            }
        }

        mStartList.setOnClickListener{
            val dialogView: View = LayoutInflater.from(context).inflate(R.layout.chose_list_dialog,null)
            val createListButton = dialogView.findViewById<MaterialButton>(R.id.createButton)
            val rvList: RecyclerView = dialogView.findViewById(R.id.recyclerView)
            val listDialog = MaterialAlertDialogBuilder(requireContext())
                    .setView(dialogView)
                    .show()

            val titleAdapter = ListTitlesAdapter(onItemClick = {
                findNavController().navigate(RandomListFragmentDirections.actionRandomListFragmentToListFragment(it))
                listDialog.dismiss()
            }, onEditClick = {
                CreateEditListDialog.newInstance(title = it)
                    .show(childFragmentManager, "createEditDialog")

            }, onDeleteClick = {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Delete list?")
                    .setMessage("This action cannot be undone")
                    .setPositiveButton("yes") { _, _ ->
                        mRepo.deleteItemsByTitle(title = it)
                    }
                    .setNegativeButton("cancel") { _, _ ->}
                    .show()
            })

            mRepo.getTitles().observe(viewLifecycleOwner, {
                titleAdapter.submitList(it)
            })

            rvList.apply {
                adapter = titleAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            createListButton.setOnClickListener {
                CreateEditListDialog.newInstance(title = null)
                    .show(parentFragmentManager, "createEditDialog")            }
        }

        mStartMatches.setOnClickListener {
            val dialogView: View = LayoutInflater.from(context).inflate(R.layout.create_matches_dialog, null)
            val numberOfMatchesEt = dialogView.findViewById<TextInputEditText>(R.id.number_matches)
            val numberOfBurnedEt = dialogView.findViewById<TextInputEditText>(R.id.number_burned)
            val createListButton = dialogView.findViewById<MaterialButton>(R.id.createButton)
            val dialog = MaterialAlertDialogBuilder(view.context)
                .setView(dialogView)
                .show()

            createListButton.setOnClickListener {
                if(InputFilters.matchesFieldsFilter(numberOfMatchesEt, numberOfBurnedEt)){
                        findNavController().navigate(RandomListFragmentDirections.actionRandomListFragmentToMatchesFragment()
                            .setNumberBurned(numberOfBurnedEt.text.toString().toInt())
                            .setNumberMatches(numberOfMatchesEt.text.toString().toInt()))
                        dialog.dismiss()
                }
            }
        }
    }

}
