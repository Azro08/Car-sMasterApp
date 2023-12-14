package com.chugay.cartech.room.clients.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chugay.cartech.model.Client

@Dao
interface ClientsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertClient(client: Client)

    @Delete
    suspend fun deleteClient(client: Client)

    @Query("SELECT * FROM clients_table")
    fun getAllClients() : LiveData<List<Client>>


}