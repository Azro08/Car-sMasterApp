package com.chugay.cartech.ui.admins_ui.fragments.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chugay.cartech.R
import com.chugay.cartech.adapter.RvOrderAdapter
import com.chugay.cartech.databinding.FragmentOrdersBinding
import com.chugay.cartech.helper.Constants
import com.chugay.cartech.sharedpref.MySharedPreferences
import com.chugay.cartech.ui.admins_ui.fragments.orders.viewmodel.AdminOrdersViewModel

class OrdersFragment : Fragment() {
    private var _binding : FragmentOrdersBinding ?= null
    private val binding get() = _binding!!
    private var adapter : RvOrderAdapter?=null
    lateinit var orderVm : AdminOrdersViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOrdersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        orderVm = ViewModelProvider(requireActivity())[AdminOrdersViewModel::class.java]
        loadOrders()
    }

    private fun loadOrders() {
        val sp = MySharedPreferences(requireContext())
        orderVm.readAllOrders.observe(requireActivity()){
            adapter = RvOrderAdapter(it){ order ->
                findNavController().navigate(R.id.nav_admin_order_details, bundleOf(Pair(Constants.ORDER_ID, order.id)))
            }
            binding.rvAllOrders.setHasFixedSize(true)
            binding.rvAllOrders.layoutManager = LinearLayoutManager(requireContext())
            binding.rvAllOrders.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}