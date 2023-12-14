package com.chugay.cartech.room.clients.repository

import androidx.lifecycle.LiveData
import com.chugay.cartech.model.Client
import com.chugay.cartech.room.clients.dao.ClientsDao

class ClientsRepository (private val clientsDao: ClientsDao) {

    val readAllClients : LiveData<List<Client>> = clientsDao.getAllClients()

    suspend fun addClient(client: Client){
        clientsDao.insertClient(client)
    }

}