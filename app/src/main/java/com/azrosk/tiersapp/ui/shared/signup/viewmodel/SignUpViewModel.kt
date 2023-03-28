package com.azrosk.tiersapp.ui.shared.signup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.azrosk.tiersapp.model.Client
import com.azrosk.tiersapp.room.MyDataBase
import com.azrosk.tiersapp.room.clients.repository.ClientsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel (application: Application) : AndroidViewModel(application) {
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