package com.azrosk.tiersapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azrosk.tiersapp.databinding.ClientServiceViewViewholderBinding
import com.azrosk.tiersapp.model.Product

class RvServicesAdapter
    (private val servicesList : List<Product>,
    private val listener : (service : Product) -> Unit) : RecyclerView.Adapter<RvServicesAdapter.MyViewHolder>() {

    class MyViewHolder(listener: (service: Product) -> Unit,
                       var binding : ClientServiceViewViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root)
    {
        private val context = itemView.context!!
        private var service : Product?=null
        fun bind(myService: Product){
            binding.tvServiceTitle.text = myService.title
            binding.tvServicePrice.text = myService.price.toString() + "р."
            service = myService
        }

        init {
            binding.ll.setOnClickListener { listener(service!!) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(listener, ClientServiceViewViewholderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(servicesList[position])
    }

    override fun getItemCount(): Int {
        return servicesList.size
    }

}