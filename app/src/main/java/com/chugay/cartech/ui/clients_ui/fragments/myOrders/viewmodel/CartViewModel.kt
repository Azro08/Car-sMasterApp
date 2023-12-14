package com.chugay.cartech.ui.clients_ui.fragments.myOrders.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.chugay.cartech.model.Cart
import com.chugay.cartech.room.MyDataBase
import com.chugay.cartech.room.cart.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class CartViewModel  (application: Application) : AndroidViewModel(application) {
    val readCartItems : LiveData<List<Cart>>
    private val repository : CartRepository
    init {
        val cartDao = MyDataBase.getInstance(application).getCartDao()
        repository = CartRepository(cartDao)
        readCartItems = repository.readCartItems
    }

    fun addToCart (cartItem: Cart){
        viewModelScope.launch (Dispatchers.IO){
            repository.addToCart(cartItem)
        }
    }

    fun deleteFromCart(cartItem: Cart){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteFromCart(cartItem)
        }
    }

}