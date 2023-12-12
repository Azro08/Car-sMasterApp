package com.chugay.cartech.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admins_table")
class Admin (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo
    val email : String = "",

    @ColumnInfo
    val password : String = "")