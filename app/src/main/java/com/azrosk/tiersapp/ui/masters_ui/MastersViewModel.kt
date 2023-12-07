package com.azrosk.tiersapp.ui.masters_ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.azrosk.tiersapp.model.Master
import com.azrosk.tiersapp.model.Order
import com.azrosk.tiersapp.room.MyDataBase
import com.azrosk.tiersapp.room.masters.repository.MastersRepository
import com.azrosk.tiersapp.room.orders.repository.OrderRepository
import kotlinx.coroutines.launch

class MastersViewModel (application: Application) : AndroidViewModel(application) {
    private val repository : MastersRepository
    private val ordersRepository : OrderRepository
    var readMaster = MutableLiveData<Master?>()
    val readAllOrders : LiveData<List<Order>>
    init {
        val mastersDao = MyDataBase.getInstance(application).getMastersDao()
        repository = MastersRepository(mastersDao)
        val ordersDao = MyDataBase.getInstance(application).getOrderDao()
        ordersRepository = OrderRepository(ordersDao)
        readAllOrders = ordersRepository.readAllOrders
    }

    fun getMasterById(email: String) {
        viewModelScope.launch {
            readMaster.value = repository.getMaster(email)
        }
    }

}