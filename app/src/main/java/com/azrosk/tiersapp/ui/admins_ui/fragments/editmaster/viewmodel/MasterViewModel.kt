package com.azrosk.tiersapp.ui.admins_ui.fragments.editmaster.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.azrosk.tiersapp.model.Master
import com.azrosk.tiersapp.room.MyDataBase
import com.azrosk.tiersapp.room.masters.repository.MastersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MasterViewModel (application: Application) : AndroidViewModel(application) {
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