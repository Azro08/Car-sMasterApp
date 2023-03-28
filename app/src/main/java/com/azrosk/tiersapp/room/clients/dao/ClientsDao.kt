package com.azrosk.tiersapp.room.clients.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.azrosk.tiersapp.model.Admin
import com.azrosk.tiersapp.model.Client

@Dao
interface ClientsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertClient(client: Client)

    @Delete
    suspend fun deleteClient(client: Client)

    @Query("SELECT * FROM clients_table")
    fun getAllClients() : LiveData<List<Client>>


}