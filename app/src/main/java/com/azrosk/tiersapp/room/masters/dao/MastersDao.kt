package com.azrosk.tiersapp.room.masters.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.azrosk.tiersapp.model.Master

@Dao
interface MastersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMaster(master: Master)

    @Update
    suspend fun updateMaster(master: Master)

    @Delete
    suspend fun deleteMaster(master: Master)

    @Query("SELECT * FROM masters_table")
    fun getAllMasters() : LiveData<List<Master>>

}