package com.azrosk.tiersapp.ui.admins_ui.fragments.editService

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.azrosk.tiersapp.R
import com.azrosk.tiersapp.databinding.FragmentEditServiceBinding
import com.azrosk.tiersapp.helper.Constants
import com.azrosk.tiersapp.model.Product
import com.azrosk.tiersapp.ui.admins_ui.activities.MainActivity
import com.azrosk.tiersapp.ui.admins_ui.fragments.editService.viewmodel.EditServiceViewModel
import com.azrosk.tiersapp.ui.admins_ui.fragments.home.viewmodel.ServiceViewModel
import com.azrosk.tiersapp.ui.clients_ui.actitvity.ClientsActivity

class FragmentEditService : Fragment() {
    private var _binding : FragmentEditServiceBinding?=null
    private val binding get() = _binding!!
    lateinit var serviceVm : EditServiceViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentEditServiceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        serviceVm = ViewModelProvider(requireActivity())[EditServiceViewModel::class.java]
        val serviceId = arguments?.getInt(Constants.ET_SERVICE_ID)

        if (serviceId != null){
            serviceVm.readAllProducts.observe(requireActivity()){
                for (i in it){
                    if (serviceId == i.id) loadDetails(i)
                }
            }
        } else{
            binding.editContainer.visibility = View.GONE
            binding.editErr.visibility = View.VISIBLE
        }

    }

    private fun loadDetails(i: Product) {
        binding.etEditServiceTitle.setText(i.title)
        binding.etEditServicePrice.setText(i.price.toString())
        binding.etEditServiceDetails.setText(i.description)
        binding.etEditRecomMastersService.setText(i.recommended_masters)
        binding.btnEditService.setOnClickListener {

            when {
                TextUtils.isEmpty(binding.etEditServiceTitle.text.toString().trim { it <= ' ' }) -> {
                    binding.etEditServiceTitle.error = "fill out"
                }
                TextUtils.isEmpty(binding.etEditServiceDetails.text.toString().trim { it <= ' ' }) -> {
                    binding.etEditServiceDetails.error = "fill out"
                }
                TextUtils.isEmpty(binding.etEditServicePrice.text.toString().trim { it <= ' ' }) -> {
                    binding.etEditServicePrice.error = "fill out"
                }
                else -> {
                    val title = binding.etEditServiceTitle.text.toString()
                    val price = binding.etEditServicePrice.text.toString().toDouble()
                    val description = binding.etEditServiceDetails.text.toString()
                    val recMasters = binding.etEditRecomMastersService.text.toString()
                    val updatedProduct = Product(id = i.id, title = title, recommended_masters = recMasters, price = price, description = description)
                    Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
                    serviceVm.updateProduct(updatedProduct)
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}