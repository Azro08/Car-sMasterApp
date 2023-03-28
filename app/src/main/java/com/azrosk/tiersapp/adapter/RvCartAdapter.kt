package com.azrosk.tiersapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azrosk.tiersapp.databinding.CartViewHolderBinding
import com.azrosk.tiersapp.model.Cart

class RvCartAdapter (private val cartList : List<Cart>,
                     private val listener : (cart : Cart) -> Unit) : RecyclerView.Adapter<RvCartAdapter.MyViewHolder>()
{
    class MyViewHolder(listener: (cart: Cart) -> Unit,
                       var binding : CartViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root)
    {
        private val context = itemView.context!!
        private var cart : Cart ?=null
        fun bind(myCart: Cart){
            binding.cartTitle.text = myCart.title
            binding.cartPrice.text = myCart.price.toString()
            cart = myCart
        }

        init {
            binding.llOrder.setOnClickListener { listener(cart!!) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(listener, CartViewHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

}