package com.azrosk.tiersapp.model

import android.icu.text.CaseMap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class Cart (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo
    val service_id : Int = 0,

    @ColumnInfo
    val user : String = "",

    @ColumnInfo
    val title: String = "",

    @ColumnInfo
    val price : Double = 0.0
        )