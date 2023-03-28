package com.azrosk.tiersapp.room.clients.repository

import androidx.lifecycle.LiveData
import com.azrosk.tiersapp.model.Admin
import com.azrosk.tiersapp.model.Client
import com.azrosk.tiersapp.room.admins.dao.AdminDao
import com.azrosk.tiersapp.room.clients.dao.ClientsDao

class ClientsRepository (private val clientsDao: ClientsDao) {

    val readAllClients : LiveData<List<Client>> = clientsDao.getAllClients()

    suspend fun addClient(client: Client){
        clientsDao.insertClient(client)
    }

}