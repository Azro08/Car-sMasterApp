package com.azrosk.tiersapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azrosk.tiersapp.databinding.ClientServiceViewViewholderBinding
import com.azrosk.tiersapp.databinding.OrderViewholderBinding
import com.azrosk.tiersapp.model.Order
import com.azrosk.tiersapp.model.Product

class RvOrderAdapter
    (private val orderList : List<Order>,
    private val listener : (order : Order) -> Unit) : RecyclerView.Adapter<RvOrderAdapter.MyViewHolder>()
{
    class MyViewHolder(listener: (order: Order) -> Unit,
                       var binding : OrderViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root)
    {
        private val context = itemView.context!!
        private var order : Order?=null
        fun bind(myOrder: Order){
            binding.orderTitle.text = myOrder.service_title
            binding.orderTime.text = myOrder.order_time
            order = myOrder
        }

        init {
            binding.llOrder.setOnClickListener { listener(order!!) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(listener, OrderViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

}