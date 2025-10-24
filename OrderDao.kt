package com.oakay.contracts.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY dueDate ASC")
    fun getAll(): Flow<List<Order>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSync(order: Order): Long

    @Update
    fun updateSync(order: Order)

    @Query("SELECT * FROM orders WHERE id = :id LIMIT 1")
    fun getByIdSync(id: Long): Order?
}
