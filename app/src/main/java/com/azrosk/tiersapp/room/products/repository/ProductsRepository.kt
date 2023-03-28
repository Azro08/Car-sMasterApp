package com.azrosk.tiersapp.room.products.repository

import androidx.lifecycle.LiveData
import com.azrosk.tiersapp.model.Product
import com.azrosk.tiersapp.room.products.dao.ProductsDao

class ProductsRepository(private val productsDao: ProductsDao) {

    val readAllProducts : LiveData<List<Product>> = productsDao.getAllProducts()

    suspend fun addProduct(product: Product){
        productsDao.addProduct(product)
    }

    suspend fun updateProduct(product: Product){
        productsDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product){
        productsDao.deleteProduct(product)
    }

}