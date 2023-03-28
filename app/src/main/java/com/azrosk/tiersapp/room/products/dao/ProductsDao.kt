package com.azrosk.tiersapp.room.products.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.azrosk.tiersapp.model.Client
import com.azrosk.tiersapp.model.Product

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Query("SELECT * FROM products_table")
    fun getAllProducts() : LiveData<List<Product>>

}