package com.azrosk.tiersapp.room.orders.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.azrosk.tiersapp.model.Order

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToOrders(order: Order)

    @Delete
    suspend fun delete(order: Order)

    @Update
    suspend fun updateOrder(order: Order)

    @Query("SELECT * FROM orders_table")
    fun getAllOrders() : LiveData<List<Order>>
}