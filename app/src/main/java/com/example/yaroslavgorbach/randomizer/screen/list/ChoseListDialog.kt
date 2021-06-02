package com.example.yaroslavgorbach.randomizer.screen.list

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yaroslavgorbach.randomizer.data.local.Repo
import com.example.yaroslavgorbach.randomizer.databinding.DialogChoseListBinding
import com.example.yaroslavgorbach.randomizer.di.appComponent
import com.example.yaroslavgorbach.randomizer.screen.nav
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChoseListDialog : DialogFragment() {
    @Inject
    lateinit var repo: Repo
    private lateinit var binding: DialogChoseListBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogChoseListBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .show()

        val titleAdapter = ListTitlesAdapter(onItemClick = {
            nav.openList(it)
            dialog.dismiss()
        }, onEditClick = {
            nav.showCreateEditListDialog(title = it)
        }, onDeleteClick = {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete list?")
                .setMessage("This action cannot be undone") // TODO: 4/11/2021 translate
                .setPositiveButton("yes") { _, _ ->
                    GlobalScope.launch {
                        repo.deleteItemsByTitle(title = it)
                    }
                }
                .setNegativeButton("cancel") { _, _ -> }
                .show()
        })

        // TODO: 4/16/2021 fix it
        repo.getTitles().observeForever {
            titleAdapter.submitList(it)
        }

        binding.list.apply {
            adapter = titleAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        binding.createList.setOnClickListener {
            nav.showCreateEditListDialog(null)
        }

        return dialog
    }

}