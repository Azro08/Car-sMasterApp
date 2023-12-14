package com.chugay.cartech.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chugay.cartech.R

class RvMasterAdapter(private val busyTimes: ArrayList<String>) :
    RecyclerView.Adapter<RvMasterAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView = itemView.findViewById(R.id.tvBusyTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.master_view_holder, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curTime = busyTimes[position]
        holder.tvTime.text = curTime.toString()
    }

    override fun getItemCount(): Int {
        return busyTimes.size
    }

}