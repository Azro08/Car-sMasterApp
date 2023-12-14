package com.chugay.cartech.ui.admins_ui.fragments.addMaster.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.chugay.cartech.model.Master
import com.chugay.cartech.room.MyDataBase
import com.chugay.cartech.room.masters.repository.MastersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMasterViewModel (application: Application) : AndroidViewModel(application) {
    val readAllMasters : LiveData<List<Master>>
    private val repository : MastersRepository
    init {
        val mastersDao = MyDataBase.getInstance(application).getMastersDao()
        repository = MastersRepository(mastersDao)
        readAllMasters = repository.readAllMasters
    }

    fun addMaster (master: Master){
        viewModelScope.launch (Dispatchers.IO){
            repository.addMaster(master)
        }
    }

    fun updateMaster(master: Master){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateMaster(master)
        }
    }

}