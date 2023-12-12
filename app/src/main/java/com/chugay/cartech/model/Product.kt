package com.chugay.cartech.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_table")
data class Product (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo
    val title : String = "",

    @ColumnInfo
    val price : Double = 0.0,

    @ColumnInfo
    val recommended_masters : String = "",

    @ColumnInfo
    val description : String = ""
)