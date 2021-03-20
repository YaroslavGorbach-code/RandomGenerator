package com.example.yaroslavgorbach.randomizer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yaroslavgorbach.randomizer.list.Database.ListItemEntity
import com.example.yaroslavgorbach.randomizer.list.Database.Repo
import com.example.yaroslavgorbach.randomizer.list.ListItemsAdapter
import com.example.yaroslavgorbach.randomizer.list.ListTitlesAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class RandomListFragment : Fragment() {
    private lateinit var mStartDice: MaterialCardView
    private lateinit var mStartCoin: MaterialCardView
    private lateinit var mStartNumber: MaterialCardView
    private lateinit var mStartList: MaterialCardView
    private lateinit var mStartMatches: MaterialCardView
    private lateinit var mRepo: Repo

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_random_list, container, false)
        mStartDice = view.findViewById(R.id.startDice)
        mStartCoin = view.findViewById(R.id.startCoin)
        mStartNumber = view.findViewById(R.id.startNumber)
        mStartList = view.findViewById(R.id.startList)
        mStartMatches = view.findViewById(R.id.startMatches)
        mRepo = Repo(requireContext())

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
                showCreateEditListDialog(currentTitle = it)
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
                showCreateEditListDialog(null)
            }
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


    private fun showCreateEditListDialog(currentTitle: String?) {
        val createListDialog: View =
            LayoutInflater.from(context).inflate(R.layout.create_list_dialog, null)
        val listTitleEt = createListDialog.findViewById<TextInputEditText>(R.id.listTitle)
        val itemTextEt = createListDialog.findViewById<TextInputEditText>(R.id.listItem)
        val createButton = createListDialog.findViewById<MaterialButton>(R.id.createButton)
        val addItemButton = createListDialog.findViewById<MaterialButton>(R.id.addItem)
        val itemsRv = createListDialog.findViewById<RecyclerView>(R.id.recyclerView)
        val listOfItems = LinkedList<String>()
        val listOfNewItems = mutableListOf<String>()
        val listOfDeletedItems = mutableListOf<String>()

        val itemAdapter = ListItemsAdapter()

        itemAdapter.addDeleteListener {
            listOfDeletedItems.add(listOfItems[it])
            listOfItems.removeAt(it)
            itemAdapter.submitList(listOfItems)
            itemAdapter.notifyDataSetChanged()
        }

        // if != null update list
        currentTitle?.let {
            mRepo.getItemsByTitle(it).observe(viewLifecycleOwner, { items ->
                repeat(items.size) { index ->
                    listOfItems.push(items[index])
                }
            })
            listTitleEt.setText(it)
            itemAdapter.submitList(listOfItems)
            createButton.text = "SAVE"
        }

        itemsRv.also {
            it.adapter = itemAdapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(createListDialog)
            .setOnCancelListener {
                if (listOfNewItems.size > 0
                    || currentTitle != listTitleEt.text.toString()
                    || listOfDeletedItems.size > 0
                ) {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Save changes?")
                        .setPositiveButton("Yes") { _, _ ->
                            changeListItems(listOfNewItems, listTitleEt, listOfDeletedItems)
                            changeListTitle(currentTitle, listTitleEt)
                        }
                        .setNegativeButton("No") { _, _ ->}
                        .show()
                }
            }
            .show()

        addItemButton.setOnClickListener {
            if (InputFilters.createListItemTestFilter(itemTextEt)){
                listOfItems.push(itemTextEt.text.toString())
                listOfNewItems.add(itemTextEt.text.toString())
                itemAdapter.submitList(listOfItems)
                itemAdapter.notifyDataSetChanged()
                itemTextEt.text = null
            }
        }

        createButton.setOnClickListener {
            if (InputFilters.createListDialogTitleFilter(listTitleEt)){
                if (listOfItems.size != 0){
                    // if title == null it means create new list
                    // if title != null it means update current list
                    if (currentTitle == null){
                        listOfItems.forEach {
                            mRepo.addItem(ListItemEntity(null, it, listTitleEt.text.toString()))
                        }
                    }else{
                        changeListItems(listOfNewItems, listTitleEt, listOfDeletedItems)
                    }
                    changeListTitle(currentTitle, listTitleEt)
                    dialog.dismiss()
                }else{
                    Toast.makeText(requireContext(), "Add at least one item", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun changeListItems(
        listOfNewItems: MutableList<String>,
        listTitleEt: TextInputEditText,
        listOfDeletedItems: MutableList<String>
    ) {
        listOfNewItems.forEach {
            mRepo.addItem(ListItemEntity(null, it, listTitleEt.text.toString()))
        }
        listOfDeletedItems.forEach {
            mRepo.deleteItem(mRepo.getItemByText(it))
        }
    }

    private fun changeListTitle(
        currentTitle: String?,
        listTitleEt: TextInputEditText
    ) {
        if (currentTitle != null && currentTitle != listTitleEt.text.toString()) {
            mRepo.changeTitle(oldTitle = currentTitle, newTitle = listTitleEt.text.toString())
        }
    }
}
