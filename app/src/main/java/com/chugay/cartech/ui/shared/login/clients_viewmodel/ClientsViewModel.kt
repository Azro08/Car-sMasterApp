package com.chugay.cartech.ui.shared.login.clients_viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.chugay.cartech.model.Client
import com.chugay.cartech.room.MyDataBase
import com.chugay.cartech.room.clients.repository.ClientsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClientsViewModel (application: Application) : AndroidViewModel(application) {
    val readAllClients : LiveData<List<Client>>
    private val repository : ClientsRepository
    init {
        val clientsDao = MyDataBase.getInstance(application).getClientDao()
        repository = ClientsRepository(clientsDao)
        readAllClients = repository.readAllClients
    }

    fun addClient (client: Client){
        viewModelScope.launch (Dispatchers.IO){
            repository.addClient(client)
        }
    }

}