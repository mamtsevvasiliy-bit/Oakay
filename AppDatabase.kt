package com.oakay.contracts.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [Order::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val inst = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "oakay_db").build()
                INSTANCE = inst
                inst
            }
        }
    }
}
