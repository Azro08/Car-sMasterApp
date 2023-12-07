package com.azrosk.tiersapp.ui.masters_ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azrosk.tiersapp.databinding.WorkItemBinding
import com.azrosk.tiersapp.model.Work

class WorksRvAdapter(private val workList: List<Work>) :
    RecyclerView.Adapter<WorksRvAdapter.WorkViewHolder>() {

    class WorkViewHolder(
        private val binding: WorkItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private var work: Work? = null
        fun bind(curWork: Work) {
            binding.textViewTitle.text = curWork.title
            binding.textViewTime.text = curWork.time
            work = curWork
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkViewHolder {
        return WorkViewHolder(
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