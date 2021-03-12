package com.example.yaroslavgorbach.randomizer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class RandomListFragment : Fragment() {
    private lateinit var mStartDice: MaterialButton
    private lateinit var mStartCoin: MaterialButton
    private lateinit var mStartNumber: MaterialButton

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_random_list, container, false)
        mStartDice = view.findViewById(R.id.dice)
        mStartCoin = view.findViewById(R.id.coin)
        mStartNumber = view.findViewById(R.id.number)
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
    }

}