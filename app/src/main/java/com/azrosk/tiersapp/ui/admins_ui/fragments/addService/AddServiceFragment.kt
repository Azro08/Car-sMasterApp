package com.azrosk.tiersapp.ui.admins_ui.fragments.addService

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.azrosk.tiersapp.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.azrosk.tiersapp.databinding.FragmentAddServiceBinding
import com.azrosk.tiersapp.model.Product
import com.azrosk.tiersapp.ui.admins_ui.activities.MainActivity
import com.azrosk.tiersapp.ui.admins_ui.fragments.addService.masters_viewmoel.AddServiceMasterViewModel
import com.azrosk.tiersapp.ui.admins_ui.fragments.addService.viewmodel.AddServiceViewModel
import com.azrosk.tiersapp.ui.admins_ui.fragments.editmaster.viewmodel.MasterViewModel
import com.azrosk.tiersapp.ui.admins_ui.fragments.home.viewmodel.ServiceViewModel
import com.azrosk.tiersapp.ui.clients_ui.actitvity.ClientsActivity


class AddServiceFragment : Fragment() {
    private var _binding : FragmentAddServiceBinding?=null
    private val binding get() = _binding!!
    lateinit var productVm : AddServiceViewModel
    lateinit var mastersVm : AddServiceMasterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddServiceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        productVm = ViewModelProvider(requireActivity())[AddServiceViewModel::class.java]
        mastersVm = ViewModelProvider(requireActivity())[AddServiceMasterViewModel::class.java]
        binding.btnAddService.setOnClickListener {
            addService()
        }

    }



    private fun addService() {
        when {
            TextUtils.isEmpty(binding.etServiceTitle.text.toString().trim { it <= ' ' }) -> {
                binding.etServiceTitle.error = "fill out"
                //                Toast.makeText(this, "Please enter email.", Toast.LENGTH_LONG).show()
            }
            TextUtils.isEmpty(binding.etServiceDetails.text.toString().trim { it <= ' ' }) -> {
                binding.etServiceDetails.error = "fill out"
            }
            TextUtils.isEmpty(binding.etServicePrice.text.toString().trim { it <= ' ' }) -> {
                binding.etServicePrice.error = "fill out"
            }

            else -> {
                val title = binding.etServiceTitle.text.toString()
                val description = binding.etServiceDetails.text.toString()
                val price = binding.etServicePrice.text.toString().toDouble()
                val recMasters = binding.etRecomMastersService.text.toString()
                val prod = Product(title = title, price = price, description = description, recommended_masters = recMasters)
                productVm.addService(prod)
                Toast.makeText(requireContext(), "Service added", Toast.LENGTH_LONG).show()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}