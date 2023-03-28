package com.azrosk.tiersapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders_table")
data class Order (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo
    val service_id : Int = 0,

    @ColumnInfo
    val service_title : String = "",

    @ColumnInfo
    val buyer : String = "",

    @ColumnInfo
    val phone_num : String = "",

    @ColumnInfo
    val order_time : String = "",

    @ColumnInfo
    val picked_master : String = "",

    @ColumnInfo
    val client_comment : String = "",

    @ColumnInfo
    val master_comment : String = "",

    @ColumnInfo
    val car_type : String = "",

    @ColumnInfo
    val car_model : String = "",

    )