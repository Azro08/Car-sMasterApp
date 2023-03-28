package com.azrosk.tiersapp.ui.admins_ui.fragments.addMaster

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.azrosk.tiersapp.databinding.FragmentAddMasterDialogBinding
import com.azrosk.tiersapp.model.Master
import com.azrosk.tiersapp.ui.admins_ui.fragments.addMaster.viewmodel.AddMasterViewModel
import com.azrosk.tiersapp.ui.admins_ui.fragments.editmaster.viewmodel.MasterViewModel

class AddMasterDialogFragment : DialogFragment() {
    private var _binding : FragmentAddMasterDialogBinding ?= null
    private val binding get() = _binding!!
    lateinit var mastersVm : AddMasterViewModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentAddMasterDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this.activity)
        builder.run { setView(binding.root) }
        mastersVm = ViewModelProvider(requireActivity())[AddMasterViewModel::class.java]
        binding.btnAddMaster.setOnClickListener {
            when {
                (TextUtils.isEmpty(binding.etAddMasterName.text.toString().trim { it <= ' ' })) -> {
                    binding.etAddMasterName.error = "fill out"
                }
                (TextUtils.isEmpty(binding.etAddMasterSurName.text.toString().trim { it <= ' ' })) -> {
                    binding.etAddMasterSurName.error = "fill out"
                }
                else -> {
                    val name =
                        binding.etAddMasterName.text.toString() + " " + binding.etAddMasterSurName.text.toString()
                    val masterExists = masterExists(name)
                    if (masterExists == 1) Toast.makeText(
                        requireContext(),
                        "Master already exists!",
                        Toast.LENGTH_SHORT
                    ).show()
                    else {
                        mastersVm.addMaster(Master(name = name))
                        dismiss()
                    }
                }
            }
        }

        return builder.create()
    }

    private fun masterExists(name: String): Int {
        var masterExists = 0
        val mastersList = getMastersList()
        masterExists = if (mastersList.contains(name)) 1
        else 0
        return masterExists
    }

    private fun getMastersList(): List<String> {
        val mastersList = mutableListOf<String>()
        mastersVm.readAllMasters.observe(requireActivity()){
            for (i in it){
                mastersList.add(i.name)
            }
        }
        return mastersList
    }
}