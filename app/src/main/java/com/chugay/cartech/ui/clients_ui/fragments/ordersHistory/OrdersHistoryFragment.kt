package com.chugay.cartech.ui.clients_ui.fragments.ordersHistory

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chugay.cartech.R
import com.chugay.cartech.adapter.RvOrderAdapter
import com.chugay.cartech.databinding.FragmentOrdersHistoryBinding
import com.chugay.cartech.helper.Constants
import com.chugay.cartech.model.Order
import com.chugay.cartech.ui.clients_ui.fragments.ordersHistory.viewmodel.OrderViewModel
import com.chugay.cartech.sharedpref.MySharedPreferences


class OrdersHistoryFragment : Fragment() {
    private var _binding : FragmentOrdersHistoryBinding?=null
    private val binding get() = _binding!!
    private var adapter : RvOrderAdapter?=null
    private lateinit var orderVm : OrderViewModel
    private val ownerList : MutableList<Order> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        _binding = FragmentOrdersHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        orderVm = ViewModelProvider(requireActivity())[OrderViewModel::class.java]
        val sp = MySharedPreferences(requireContext())
        val curUser = sp.getEmail()

        if (curUser != null){
           loadOrdersHistory(curUser)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadOrdersHistory(curUser: String) {
        orderVm.readAllOrders.observe(requireActivity()){
            for (i in it){
                if (i.buyer == curUser){
                    ownerList.add(i)
                    adapter = RvOrderAdapter(ownerList){ order ->
                        findNavController().navigate(R.id.nav_his_det, bundleOf(Pair(Constants.ORDER_ID, order.id)))
                    }
                    binding.rvOrderHistory.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvOrderHistory.setHasFixedSize(true)
                    binding.rvOrderHistory.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}