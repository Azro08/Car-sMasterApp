package com.azrosk.tiersapp.ui.shared.login.admin_viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.azrosk.tiersapp.model.Admin
import com.azrosk.tiersapp.model.Master
import com.azrosk.tiersapp.room.MyDataBase
import com.azrosk.tiersapp.room.admins.repository.AdminRepository
import com.azrosk.tiersapp.room.masters.repository.MastersRepository
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