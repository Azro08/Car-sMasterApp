package com.chugay.cartech.ui.shared.orderDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.chugay.cartech.databinding.FragmentOrderDetailsBinding
import com.chugay.cartech.helper.Constants
import com.chugay.cartech.model.Order
import com.chugay.cartech.sharedpref.MySharedPreferences
import com.chugay.cartech.ui.shared.orderDetails.viewmodel.OrderDetailsOrderViewModel
import com.chugay.cartech.ui.shared.orderDetails.viewmodel.OrderDetailsServiceViewModel

class OrderDetailsFragment : Fragment() {
    private var _binding : FragmentOrderDetailsBinding?=null
    private val binding get() = _binding!!
    lateinit var orderVm : OrderDetailsOrderViewModel
    lateinit var serviceVm : OrderDetailsServiceViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOrderDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        orderVm = ViewModelProvider(requireActivity())[OrderDetailsOrderViewModel::class.java]
        serviceVm = ViewModelProvider(requireActivity())[OrderDetailsServiceViewModel::class.java]
        val order_id = arguments?.getInt(Constants.ORDER_ID)
        if (order_id != null){
            loadOrderDetails(order_id)
        } else {
            binding.llSerDet.visibility = View.GONE
            binding.tvErrOrderDetails.visibility = View.VISIBLE
        }
        val sp = MySharedPreferences(requireContext())
        val cur_user = sp.getLoggedInBy()

        if (cur_user == Constants.ADMIN || cur_user == Constants.MASTER){
            binding.btnAddComment.visibility = View.VISIBLE
        }

        binding.btnAddComment.setOnClickListener {
            binding.etAddCommentMaster.visibility = View.VISIBLE
            binding.btnSaveOrder.visibility = View.VISIBLE
        }


    }

    private fun loadOrderDetails(orderId: Int) {
        orderVm.readAllOrders.observe(requireActivity()){
            for (i in it){
                if (orderId == i.id){
                    setDetails(i)
                    binding.btnSaveOrder.setOnClickListener {
                        addComment(i)
                    }
                }
            }
        }
    }

    private fun addComment(i: Order) {
        val comment = binding.etAddCommentMaster.text.toString()
        val title = binding.tvOrderDetailsTitle.text.toString()
        val price = binding.tvOrderDetailsPrice.text.toString().toDouble()
        val carType = binding.tvOrderDetailsCarType.text.toString()
        val carModel = binding.tvOrderDetailsCarModel.text.toString()
        val master = binding.tvOrderDetailsMaster.text.toString()
        val time = binding.tvOrderDetailsTime.text.toString()
        val cl_comment = binding.tvClientCommentDet.text.toString()
        val buyer = binding.tvOrderDetailsBuyer.text.toString()
        val num = binding.tvOrderDetailsPhoneNum.text.toString()
        val newOrder = Order(id = i.id, service_title = title, buyer = buyer, service_id = i.service_id, phone_num = num,
                                picked_master = master, car_type = carType, car_model = carModel, client_comment = cl_comment, master_comment = comment)
        orderVm.updateOrder(newOrder)
        Toast.makeText(requireContext(), "comment added", Toast.LENGTH_SHORT).show()
        binding.etAddCommentMaster.visibility = View.GONE
        binding.btnSaveOrder.visibility = View.GONE
    }

    private fun setDetails(i: Order) {
        serviceVm.readAllProducts.observe(requireActivity()){
            for (j in it){
                if (i.service_id == j.id){
                    binding.tvOrderDetailsTitle.text = j.title
                    binding.tvOrderDetailsCarModel.text = i.car_model
                    binding.tvOrderDetailsCarType.text = i.car_type
                    binding.tvOrderDetailsPrice.text = j.price.toString()
                    binding.tvOrderDetailsMaster.text = i.picked_master
                    binding.tvOrderDetailsTime.text = i.order_time
                    binding.tvOrderDetailsBuyer.text = i.buyer
                    binding.tvOrderDetailsPhoneNum.text = i.phone_num
                    binding.tvClientCommentDet.text = i.client_comment
                    binding.tvMasterCommentDet.text = i.master_comment
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}