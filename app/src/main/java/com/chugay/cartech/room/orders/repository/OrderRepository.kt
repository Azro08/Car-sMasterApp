package com.chugay.cartech.room.orders.repository

import androidx.lifecycle.LiveData
import com.chugay.cartech.model.Order
import com.chugay.cartech.room.orders.dao.OrderDao

class OrderRepository  (private val orderDao: OrderDao) {

    val readAllOrders : LiveData<List<Order>> = orderDao.getAllOrders()

    suspend fun addOrder(order: Order){
        orderDao.addToOrders(order)
    }

    suspend fun updateOrder(order: Order){
        orderDao.updateOrder(order)
    }

}