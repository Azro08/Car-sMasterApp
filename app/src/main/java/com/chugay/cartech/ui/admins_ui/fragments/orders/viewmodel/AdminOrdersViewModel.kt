package com.chugay.cartech.ui.admins_ui.fragments.orders.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.chugay.cartech.model.Order
import com.chugay.cartech.room.MyDataBase
import com.chugay.cartech.room.orders.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminOrdersViewModel (application: Application) : AndroidViewModel(application) {
    val readAllOrders : LiveData<List<Order>>
    private val repository : OrderRepository
    init {
        val orderDao = MyDataBase.getInstance(application).getOrderDao()
        repository = OrderRepository(orderDao)
        readAllOrders = repository.readAllOrders
    }

    fun addOrder (order: Order){
        viewModelScope.launch (Dispatchers.IO){
            repository.addOrder(order)
        }
    }

    fun updateOrder(order: Order){
        viewModelScope.launch((Dispatchers.IO)){
            repository.updateOrder(order)
        }
    }

}