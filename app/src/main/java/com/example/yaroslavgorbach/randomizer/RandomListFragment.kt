package com.example.yaroslavgorbach.randomizer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yaroslavgorbach.randomizer.list.Database.ListItemEntity
import com.example.yaroslavgorbach.randomizer.list.Database.Repo
import com.example.yaroslavgorbach.randomizer.list.ListItemsAdapter
import com.example.yaroslavgorbach.randomizer.list.ListTitlesAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class RandomListFragment : Fragment() {
    private lateinit var mStartDice: MaterialButton
    private lateinit var mStartCoin: MaterialButton
    private lateinit var mStartNumber: MaterialButton
    private lateinit var mStartList: MaterialButton
    private lateinit var mRepo: Repo

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_random_list, container, false)
        mStartDice = view.findViewById(R.id.dice)
        mStartCoin = view.findViewById(R.id.coin)
        mStartNumber = view.findViewById(R.id.number)
        mStartList = view.findViewById(R.id.list)
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
                showCreateEditItemDialog(title = it)
            }, onDeleteClick = {
                mRepo.deleteItemsByTitle(title = it)
            })

            mRepo.getTitles().observe(viewLifecycleOwner, {
                titleAdapter.submitList(it)
            })

            rvList.apply {
                adapter = titleAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }

            createListButton.setOnClickListener {
                showCreateEditItemDialog(null)
            }
            }
        }

    private fun showCreateEditItemDialog(title: String?) {
        val createListDialog: View =
            LayoutInflater.from(context).inflate(R.layout.create_list_dialog, null)
        val listTitle = createListDialog.findViewById<TextInputEditText>(R.id.listTitle)
        val itemText = createListDialog.findViewById<TextInputEditText>(R.id.listItem)
        val createButton = createListDialog.findViewById<MaterialButton>(R.id.createButton)
        val addItemButton = createListDialog.findViewById<MaterialButton>(R.id.addItem)
        val itemsRv = createListDialog.findViewById<RecyclerView>(R.id.recyclerView)
        val listOfItems = LinkedList<String>()
        val listOfNewItems = mutableListOf<String>()
        val listOfDeletedItems= mutableListOf<String>()

        val itemAdapter = ListItemsAdapter()

        itemAdapter.addDeleteListener {
            listOfDeletedItems.add(listOfItems[it])
            listOfItems.removeAt(it)
            itemAdapter.submitList(listOfItems)
            itemAdapter.notifyDataSetChanged()
        }

        // if update list
        title?.let {
            mRepo.getItemsByTitle(it).observe(viewLifecycleOwner, {items->
                repeat(items.size) {index->
                    listOfItems.push(items[index])
                }
            })
            listTitle.setText(it)
            itemAdapter.submitList(listOfItems)
            createButton.text = "SAVE"
        }

        itemsRv.also {
            it.adapter = itemAdapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(createListDialog)
            .show()

        addItemButton.setOnClickListener {
            listOfItems.push(itemText.text.toString())
            listOfNewItems.add(itemText.text.toString())
            itemAdapter.submitList(listOfItems)
            itemAdapter.notifyDataSetChanged()
            itemText.text = null
        }


        createButton.setOnClickListener {

            // if title == null it means create new list
            // if title != null it means update current list
            if (title == null){
                listOfItems.forEach {
                    mRepo.addItem(ListItemEntity(null, it, listTitle.text.toString()))
                }
            }else{
                listOfNewItems.forEach {
                    mRepo.addItem(ListItemEntity(null, it, listTitle.text.toString()))
                }
                listOfDeletedItems.forEach {
                    mRepo.deleteItem(mRepo.getItemByText(it))
                }
            }

            if (title!= null && title!= listTitle.text.toString()){
                mRepo.changeTitle(oldTitle = title, newTitle = listTitle.text.toString())
            }
            dialog.dismiss()
        }
    }
}
