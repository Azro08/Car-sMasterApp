package com.chugay.cartech.room.cart.repository

import androidx.lifecycle.LiveData
import com.chugay.cartech.model.Cart
import com.chugay.cartech.room.cart.dao.CartDao

class CartRepository (private val cartDao: CartDao) {

    suspend fun addToCart(cartItem : Cart){
        cartDao.addToCar(cartItem)
    }

    suspend fun deleteFromCart(cartItem: Cart){
        cartDao.deleteFromCart(cartItem)
    }

    val readCartItems : LiveData<List<Cart>> = cartDao.readCartItems()

}