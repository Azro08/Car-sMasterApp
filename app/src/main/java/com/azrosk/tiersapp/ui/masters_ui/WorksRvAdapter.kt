package com.azrosk.tiersapp.ui.masters_ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azrosk.tiersapp.databinding.WorkItemBinding
import com.azrosk.tiersapp.model.Order

class WorksRvAdapter(
    private val workList: List<Order>,
    private val listener: (order: Order) -> Unit
) :
    RecyclerView.Adapter<WorksRvAdapter.WorkViewHolder>() {

    class WorkViewHolder(
        listener: (order: Order) -> Unit,
        private val binding: WorkItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private var order: Order? = null
        fun bind(curOrder: Order) {
            binding.textViewTitle.text = curOrder.service_title
            binding.textViewTime.text = curOrder.order_time
            order = curOrder
        }

        init {
            binding.root.setOnClickListener { listener(order!!) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkViewHolder {
        return WorkViewHolder(
            listener,
            WorkItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return workList.size
    }

    override fun onBindViewHolder(holder: WorkViewHolder, position: Int) {
        holder.bind(workList[position])
    }

}