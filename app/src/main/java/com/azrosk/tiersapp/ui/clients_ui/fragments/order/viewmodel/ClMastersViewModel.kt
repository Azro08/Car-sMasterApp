package com.azrosk.tiersapp.ui.clients_ui.fragments.order.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.azrosk.tiersapp.model.Master
import com.azrosk.tiersapp.model.Order
import com.azrosk.tiersapp.model.Product
import com.azrosk.tiersapp.room.MyDataBase
import com.azrosk.tiersapp.room.masters.repository.MastersRepository
import com.azrosk.tiersapp.room.orders.repository.OrderRepository
import com.azrosk.tiersapp.room.products.repository.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClMastersViewModel (application: Application) : AndroidViewModel(application) {

     var readAllMasters : LiveData<List<Master>>
     var readAllOrders : LiveData<List<Order>>
     var readAllProducts : LiveData<List<Product>>



    private val masterRepository : MastersRepository
    private val orderRepository : OrderRepository
    private val productRepository : ProductsRepository

    init {
        val mastersDao = MyDataBase.getInstance(application).getMastersDao()
        val orderDao = MyDataBase.getInstance(application).getOrderDao()
        val productsDao = MyDataBase.getInstance(application).getProductsDao()

        orderRepository = OrderRepository(orderDao)
        readAllOrders =  orderRepository.readAllOrders

        masterRepository = MastersRepository(mastersDao)
        readAllMasters = masterRepository.readAllMasters

        productRepository = ProductsRepository(productsDao)
        readAllProducts = productRepository.readAllProducts
    }

    fun addOrder (order: Order){
        viewModelScope.launch (Dispatchers.IO){
            orderRepository.addOrder(order)
        }
    }

    fun updateOrder(order: Order){
        viewModelScope.launch((Dispatchers.IO)){
            orderRepository.updateOrder(order)
        }
    }


    fun addMaster (master: Master){
        viewModelScope.launch (Dispatchers.IO){
            masterRepository.addMaster(master)
        }
    }

    fun updateMaster(master: Master){
        viewModelScope.launch(Dispatchers.IO){
            masterRepository.updateMaster(master)
        }
    }

    fun addService (product: Product){
        viewModelScope.launch (Dispatchers.IO){
            productRepository.addProduct(product)
        }
    }

    fun updateProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO){
            productRepository.updateProduct(product)
        }
    }

    fun deleteProduct ( product: Product){
        viewModelScope.launch (Dispatchers.IO){
            productRepository.deleteProduct(product)
        }
    }

}