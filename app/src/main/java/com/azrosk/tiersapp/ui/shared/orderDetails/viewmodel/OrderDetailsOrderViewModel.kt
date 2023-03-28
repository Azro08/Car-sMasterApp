package com.azrosk.tiersapp.ui.shared.orderDetails.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.azrosk.tiersapp.model.Order
import com.azrosk.tiersapp.room.MyDataBase
import com.azrosk.tiersapp.room.orders.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderDetailsOrderViewModel (application: Application) : AndroidViewModel(application) {
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