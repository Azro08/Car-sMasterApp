package com.azrosk.tiersapp.room.admins.repository

import androidx.lifecycle.LiveData
import com.azrosk.tiersapp.model.Admin
import com.azrosk.tiersapp.room.admins.dao.AdminDao

class AdminRepository (private val adminDao: AdminDao) {

    val readAllAdmins : LiveData<Admin> = adminDao.getAllAdmins()

    suspend fun addAdmin(admin: Admin){
        adminDao.insert(admin)
    }

}