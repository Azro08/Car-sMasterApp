package com.azrosk.tiersapp.room.masters.repository

import androidx.lifecycle.LiveData
import com.azrosk.tiersapp.model.Master
import com.azrosk.tiersapp.room.masters.dao.MastersDao

class MastersRepository  (private val mastersDao: MastersDao) {

    val readAllMasters : LiveData<List<Master>> = mastersDao.getAllMasters()

    suspend fun addMaster(master: Master){
        mastersDao.addMaster(master)
    }

    suspend fun updateMaster(master: Master){
        mastersDao.updateMaster(master)
    }

}