package com.chugay.cartech.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chugay.cartech.helper.ArrayListConverter
import com.chugay.cartech.model.*
import com.chugay.cartech.room.admins.dao.AdminDao
import com.chugay.cartech.room.cart.dao.CartDao
import com.chugay.cartech.room.clients.dao.ClientsDao
import com.chugay.cartech.room.masters.dao.MastersDao
import com.chugay.cartech.room.orders.dao.OrderDao
import com.chugay.cartech.room.products.dao.ProductsDao

@TypeConverters(ArrayListConverter::class)
@Database(entities = [Admin::class, Client::class, Product::class, Master::class, Order::class, Cart::class], version = 1, exportSchema = false)
abstract class MyDataBase : RoomDatabase() {
    abstract fun getAdminDao() : AdminDao
    abstract fun getClientDao() : ClientsDao
    abstract fun getProductsDao() : ProductsDao
    abstract fun getMastersDao() : MastersDao
    abstract fun getOrderDao() : OrderDao
    abstract fun getCartDao() : CartDao

    companion object{
        @Volatile
        private var database : MyDataBase?= null

        @Synchronized
        fun getInstance(context: Context) : MyDataBase {
            return if (database == null){
                database = Room.databaseBuilder(context, MyDataBase::class.java, "db").build()
                database as MyDataBase
            } else {
                database as MyDataBase
            }
        }
    }

}