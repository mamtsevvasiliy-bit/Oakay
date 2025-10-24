package com.oakay.contracts.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val clientName: String,
    val orderName: String,
    val amount: Double,
    val prepayment: Double,
    val startDate: Long,
    val dueDate: Long,
    val status: String, // IN_PROGRESS or COMPLETED
    val mediaJson: String? = null,
    val notified: Boolean = false
)
