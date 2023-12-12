package com.chugay.cartech.room.admins.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chugay.cartech.model.Admin

@Dao
interface AdminDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(admin: Admin)

    @Delete
    suspend fun delete(admin: Admin)

    @Query("SELECT * FROM admins_table")
    fun getAllAdmins() : LiveData<Admin>

//    @Query("SELECT EXISTS (SELECT * from admins_table where email=:email and password=:password)")
//    suspend fun checkIfExists(email :String, password:String) : Boolean

}