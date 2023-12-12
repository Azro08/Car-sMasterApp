package com.chugay.cartech.room.admins.repository

import androidx.lifecycle.LiveData
import com.chugay.cartech.model.Admin
import com.chugay.cartech.room.admins.dao.AdminDao

class AdminRepository (private val adminDao: AdminDao) {

    val readAllAdmins : LiveData<Admin> = adminDao.getAllAdmins()

    suspend fun addAdmin(admin: Admin){
        adminDao.insert(admin)
    }

}