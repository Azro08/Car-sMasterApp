package com.chugay.cartech.model

import androidx.room.*
import com.chugay.cartech.helper.ArrayListConverter
import kotlin.collections.ArrayList

@Entity(tableName = "masters_table")
data class Master (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo
    val name : String = "",

    @ColumnInfo
    val email : String = "",

    @ColumnInfo
    val password : String = "",

    @ColumnInfo
    @TypeConverters(ArrayListConverter::class)
    val busy_times : ArrayList<String> = arrayListOf(),

        )
