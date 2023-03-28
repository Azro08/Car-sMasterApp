package com.azrosk.tiersapp.ui.clients_ui.fragments.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.azrosk.tiersapp.model.Product
import com.azrosk.tiersapp.room.MyDataBase
import com.azrosk.tiersapp.room.products.repository.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientServiceViewModel (application: Application) : AndroidViewModel(application) {
    val readAllProducts : LiveData<List<Product>>
    private val repository : ProductsRepository
    init {
        val productsDao = MyDataBase.getInstance(application).getProductsDao()
        repository = ProductsRepository(productsDao)
        readAllProducts = repository.readAllProducts
    }

    fun addService (product: Product){
        viewModelScope.launch (Dispatchers.IO){
            repository.addProduct(product)
        }
    }

    fun updateProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateProduct(product)
        }
    }

    fun deleteProduct ( product: Product){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteProduct(product)
        }
    }

}