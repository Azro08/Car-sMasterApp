package com.chugay.cartech.room.masters.repository

import androidx.lifecycle.LiveData
import com.chugay.cartech.model.Master
import com.chugay.cartech.room.masters.dao.MastersDao

class MastersRepository  (private val mastersDao: MastersDao) {

    val readAllMasters : LiveData<List<Master>> = mastersDao.getAllMasters()

    suspend fun addMaster(master: Master){
        mastersDao.addMaster(master)
    }

    suspend fun updateMaster(master: Master){
        mastersDao.updateMaster(master)
    }

    suspend fun getMaster(email : String): Master? {
        return mastersDao.getMasterById(email)
    }
}