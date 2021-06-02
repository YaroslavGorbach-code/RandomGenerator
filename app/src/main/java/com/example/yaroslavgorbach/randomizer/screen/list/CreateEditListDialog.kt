package com.example.yaroslavgorbach.randomizer.screen.list

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yaroslavgorbach.randomizer.InputFilters
import com.example.yaroslavgorbach.randomizer.data.local.ListItemEntity
import com.example.yaroslavgorbach.randomizer.data.local.Repo
import com.example.yaroslavgorbach.randomizer.databinding.DialogCreateListBinding
import com.example.yaroslavgorbach.randomizer.di.appComponent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CreateEditListDialog: DialogFragment() {
    private val listOfItems = LinkedList<String>()
    private val listOfNewItems = mutableListOf<String>()
    private val listOfDeletedItems = mutableListOf<String>()
    @Inject lateinit var mRepo: Repo


    companion object Args {
        fun argsOf(title: String?)
                = bundleOf("title" to title)
        private val CreateEditListDialog.title get() = requireArguments()["title"] as String?
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogCreateListBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
        val itemAdapter = ListItemsAdapter()

        itemAdapter.addDeleteListener {
            listOfDeletedItems.add(listOfItems[it])
            listOfItems.removeAt(it)
            itemAdapter.submitList(listOfItems)
            itemAdapter.notifyDataSetChanged()
        }

        // if != null update list
        title?.let {
            lifecycleScope.launch {
                mRepo.getItemsByTitle(it).also { items ->
                    repeat(items.size) { index ->
                        listOfItems.push(items[index])
                    }
                }
            }

            binding.titleText.setText(it)
            itemAdapter.submitList(listOfItems)
            binding.createList.text = "SAVE"
        }

        binding.listItems.also {
            it.adapter = itemAdapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }

        binding.addItem.setOnClickListener {
            if (InputFilters.createListItemTestFilter(binding.itemText)) {
                listOfItems.push(binding.itemText.text.toString())
                listOfNewItems.add(binding.itemText.text.toString())
                itemAdapter.submitList(listOfItems)
                itemAdapter.notifyDataSetChanged()
                binding.itemText.text = null
            }
        }

        binding.createList.setOnClickListener {
            if (InputFilters.createListDialogTitleFilter(binding.titleText)) {
                // if title == null it means create new list
                // if title != null it means update current list
                if (title == null) {
                    listOfItems.forEach {
                        GlobalScope.launch {
                            mRepo.addItem(ListItemEntity(null, it, binding.titleText.text.toString()))
                        }
                    }
                } else {
                    changeListItems(listOfNewItems, binding.titleText, listOfDeletedItems)
                }
                changeListTitle(title, binding.titleText)
                this.dismiss()
            }
        }

        dialog.setOnCancelListener {
            if (listOfNewItems.size > 0
                || listOfDeletedItems.size > 0
                || binding.titleText.text.toString().isNotEmpty() && listOfItems.isNotEmpty()
                && title != binding.titleText.text.toString()

            ) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Save changes?")
                    .setPositiveButton("Yes") { _, _ ->
                        changeListItems(listOfNewItems, binding.titleText, listOfDeletedItems)
                        changeListTitle(title, binding.titleText)
                    }
                    .setNegativeButton("No") { _, _ -> }
                    .show()
            }
        }

        return dialog
    }

    private fun changeListItems(
        listOfNewItems: MutableList<String>,
        listTitleEt: TextInputEditText,
        listOfDeletedItems: MutableList<String>
    ) {
        listOfNewItems.forEach {
            GlobalScope.launch {
                mRepo.addItem(ListItemEntity(null, it, listTitleEt.text.toString()))
            }
        }
        listOfDeletedItems.forEach {
            GlobalScope.launch {
                mRepo.deleteItem(mRepo.getItemByText(it))
            }
        }
    }

    private fun changeListTitle(
        currentTitle: String?,
        listTitleEt: TextInputEditText
    ) {
        if (currentTitle != null && currentTitle != listTitleEt.text.toString()) {
            GlobalScope.launch {
                mRepo.changeTitle(oldTitle = currentTitle, newTitle = listTitleEt.text.toString())
            }
        }
    }

}
