package com.chugay.cartech.ui.shared.login.admin_viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.chugay.cartech.model.Admin
import com.chugay.cartech.model.Master
import com.chugay.cartech.room.MyDataBase
import com.chugay.cartech.room.admins.repository.AdminRepository
import com.chugay.cartech.room.masters.repository.MastersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminViewModel (application: Application) : AndroidViewModel(application) {
    val readAllAdmins : LiveData<Admin>
    val readAllMasters : LiveData<List<Master>>
    private val repository : AdminRepository
    init {
        val adminDao = MyDataBase.getInstance(application).getAdminDao()
        repository = AdminRepository(adminDao)
        readAllAdmins = repository.readAllAdmins

        val masterDao = MyDataBase.getInstance(application).getMastersDao()
        val masterRepository = MastersRepository(masterDao)
        readAllMasters = masterRepository.readAllMasters
    }

    fun addAdmin (admin: Admin){
        viewModelScope.launch (Dispatchers.IO){
            repository.addAdmin(admin)
        }
    }

}