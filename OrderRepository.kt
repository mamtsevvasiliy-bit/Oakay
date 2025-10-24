package com.oakay.contracts.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class OrderRepository private constructor(context: Context) {
    private val db = AppDatabase.getInstance(context)
    private val dao = db.orderDao()

    fun getAllFlow(): Flow<List<Order>> = dao.getAll()

    fun insertBlocking(order: Order): Long {
        return runBlocking { dao.insertSync(order) }
    }

    fun updateBlocking(order: Order) {
        runBlocking { dao.updateSync(order) }
    }

    companion object {
        private var INSTANCE: OrderRepository? = null
        fun getInstance(context: Context): OrderRepository {
            return INSTANCE ?: synchronized(this) {
                val inst = OrderRepository(context)
                INSTANCE = inst
                inst
            }
        }
    }
}
