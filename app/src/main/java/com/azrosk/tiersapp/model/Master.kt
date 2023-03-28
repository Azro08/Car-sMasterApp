package com.azrosk.tiersapp.model

import androidx.room.*
import com.azrosk.tiersapp.helper.ArrayListConverter
import kotlin.collections.ArrayList

@Entity(tableName = "masters_table")
data class Master (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo
    val name : String = "",

    @ColumnInfo
    @TypeConverters(ArrayListConverter::class)
    val busy_times : ArrayList<String> = arrayListOf(),

        )
