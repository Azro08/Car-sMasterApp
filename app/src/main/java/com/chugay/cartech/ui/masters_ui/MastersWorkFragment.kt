package com.chugay.cartech.ui.masters_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chugay.cartech.R
import com.chugay.cartech.databinding.FragmentMastersWorkBinding
import com.chugay.cartech.helper.Constants
import com.chugay.cartech.model.Master
import com.chugay.cartech.model.Order
import com.chugay.cartech.sharedpref.MySharedPreferences


class MastersWorkFragment : Fragment() {
    private var _binding: FragmentMastersWorkBinding? = null
    private val binding: FragmentMastersWorkBinding get() = _binding!!
    private lateinit var mastersViewModel: MastersViewModel
    private val worksList = mutableListOf<Order>()
    private var rvAdapter: WorksRvAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMastersWorkBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mastersViewModel = ViewModelProvider(this)[MastersViewModel::class.java]
        getMaster()
    }

    private fun getMaster() {
        val sp = MySharedPreferences(requireContext())
        val email = sp.getEmail() ?: ""
        mastersViewModel.getMasterById(email)
        mastersViewModel.readMaster.observe(requireActivity()) { master ->
            if (master != null) getMasterWork(master)
            else Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMasterWork(master: Master) {
        mastersViewModel.readAllOrders.observe(requireActivity()) { orders ->
            for (order in orders) {
                if (order.picked_master == master.name) worksList.add(order)
            }
            rvAdapter = WorksRvAdapter(worksList){
                findNavController().navigate(R.id.nav_work_order_details, bundleOf(Pair(Constants.ORDER_ID, it.id)))
            }
            binding.rvWorks.setHasFixedSize(true)
            binding.rvWorks.layoutManager = LinearLayoutManager(requireContext())
            binding.rvWorks.adapter = rvAdapter
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}