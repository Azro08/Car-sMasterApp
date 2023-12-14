package com.chugay.cartech.room.cart.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chugay.cartech.model.Cart

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToCar(cartItem : Cart)

    @Delete
    suspend fun deleteFromCart (cartItem : Cart)

    @Query("SELECT * FROM cart_table")
    fun readCartItems() : LiveData<List<Cart>>

}