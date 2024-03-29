package com.chugay.cartech.ui.admins_ui.fragments.addMaster

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.chugay.cartech.databinding.FragmentAddMasterDialogBinding
import com.chugay.cartech.model.Master
import com.chugay.cartech.ui.admins_ui.fragments.addMaster.viewmodel.AddMasterViewModel
import kotlinx.coroutines.launch

class AddMasterDialogFragment : DialogFragment() {
    private var _binding: FragmentAddMasterDialogBinding? = null
    private val binding get() = _binding!!
    lateinit var mastersVm: AddMasterViewModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentAddMasterDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this.activity)
        builder.run { setView(binding.root) }
        mastersVm = ViewModelProvider(requireActivity())[AddMasterViewModel::class.java]

        binding.btnAddMaster.setOnClickListener {
            val name = binding.etAddMasterName.text.toString().trim()
            val surName = binding.etAddMasterSurName.text.toString().trim()
            val email = binding.etAddMasterEmail.text.toString().trim()
            val password = binding.etAddMasterPassword.text.toString().trim()

            if (name.isEmpty()) {
                binding.etAddMasterName.error = "Name cannot be empty"
            } else if (surName.isEmpty()) {
                binding.etAddMasterSurName.error = "Surname cannot be empty"
            } else if (email.isEmpty()) {
                binding.etAddMasterEmail.error = "Email cannot be empty"
            } else if (password.isEmpty()) {
                binding.etAddMasterPassword.error = "Password cannot be empty"
            } else {
                val fullName = "$name $surName"
                lifecycleScope.launch {
                    mastersVm.readAllMasters.observe(this@AddMasterDialogFragment) { mastersList ->
                        val exists = mastersList.any { it.name == fullName }
                        if (exists) {
                            Toast.makeText(
                                requireContext(),
                                "Master already exists!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val master = Master(name = fullName, email = email, password = password)
                            mastersVm.addMaster(master)
                            Toast.makeText(requireContext(), "Created", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        return builder.create()
    }

}