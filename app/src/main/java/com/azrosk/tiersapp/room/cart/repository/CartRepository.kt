package com.azrosk.tiersapp.room.cart.repository

import androidx.lifecycle.LiveData
import com.azrosk.tiersapp.model.Cart
import com.azrosk.tiersapp.room.cart.dao.CartDao

class CartRepository (private val cartDao: CartDao) {

    suspend fun addToCart(cartItem : Cart){
        cartDao.addToCar(cartItem)
    }

    suspend fun deleteFromCart(cartItem: Cart){
        cartDao.deleteFromCart(cartItem)
    }

    val readCartItems : LiveData<List<Cart>> = cartDao.readCartItems()

}