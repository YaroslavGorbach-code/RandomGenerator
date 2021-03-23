package com.example.yaroslavgorbach.randomizer

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yaroslavgorbach.randomizer.list.Database.ListItemEntity
import com.example.yaroslavgorbach.randomizer.list.Database.Repo
import com.example.yaroslavgorbach.randomizer.list.ListItemsAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class CreateEditListDialog private constructor() : DialogFragment() {
    private lateinit var mRepo: Repo
    private val listOfItems = LinkedList<String>()
    private val listOfNewItems = mutableListOf<String>()
    private val listOfDeletedItems = mutableListOf<String>()
    private var currentTitle: String? = null
    private lateinit var listTitleEt: TextInputEditText

    companion object {
        const val TITLE_ARG_KEY = "TITLE_ARG_KEY"
        fun newInstance(title: String?): CreateEditListDialog {
            val arg = Bundle()
            arg.putString(TITLE_ARG_KEY, title)
            val dialog = CreateEditListDialog()
            dialog.arguments = arg

            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val createListDialog: View =
            LayoutInflater.from(context).inflate(R.layout.create_list_dialog, null)
        val itemTextEt = createListDialog.findViewById<TextInputEditText>(R.id.listItem)
        val createButton = createListDialog.findViewById<MaterialButton>(R.id.createButton)
        val addItemButton = createListDialog.findViewById<MaterialButton>(R.id.addItem)
        val itemsRv = createListDialog.findViewById<RecyclerView>(R.id.recyclerView)
        val itemAdapter = ListItemsAdapter()
        mRepo = Repo(requireContext())
        currentTitle = requireArguments().getString(TITLE_ARG_KEY)
        listTitleEt = createListDialog.findViewById<TextInputEditText>(R.id.listTitle)

        itemAdapter.addDeleteListener {
            listOfDeletedItems.add(listOfItems[it])
            listOfItems.removeAt(it)
            itemAdapter.submitList(listOfItems)
            itemAdapter.notifyDataSetChanged()
        }

        // if != null update list
        currentTitle?.let {
            mRepo.getItemsByTitle(it).also { items ->
                repeat(items.size) { index ->
                    listOfItems.push(items[index])
                }
            }

            listTitleEt.setText(it)
            itemAdapter.submitList(listOfItems)
            createButton.text = "SAVE"
        }

        itemsRv.also {
            it.adapter = itemAdapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }

        addItemButton.setOnClickListener {
            if (InputFilters.createListItemTestFilter(itemTextEt)) {
                listOfItems.push(itemTextEt.text.toString())
                listOfNewItems.add(itemTextEt.text.toString())
                itemAdapter.submitList(listOfItems)
                itemAdapter.notifyDataSetChanged()
                itemTextEt.text = null
            }
        }

        createButton.setOnClickListener {
            if (InputFilters.createListDialogTitleFilter(listTitleEt)) {
                // if title == null it means create new list
                // if title != null it means update current list
                if (currentTitle == null) {
                    listOfItems.forEach {
                        mRepo.addItem(ListItemEntity(null, it, listTitleEt.text.toString()))
                    }
                } else {
                    changeListItems(listOfNewItems, listTitleEt, listOfDeletedItems)
                }
                changeListTitle(currentTitle, listTitleEt)
                this.dismiss()
            }
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(createListDialog)
            .create()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        if (listOfNewItems.size > 0
            || listOfDeletedItems.size > 0
            || listTitleEt.text.toString().isNotEmpty() && listOfItems.isNotEmpty()
            && currentTitle != listTitleEt.text.toString()

        ) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Save changes?")
                .setPositiveButton("Yes") { _, _ ->
                    changeListItems(listOfNewItems, listTitleEt, listOfDeletedItems)
                    changeListTitle(currentTitle, listTitleEt)
                }
                .setNegativeButton("No") { _, _ -> }
                .show()
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