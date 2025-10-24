package com.oakay.contracts.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.oakay.contracts.R

class ReminderWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val orderId = inputData.getLong("orderId", -1L)
        val client = inputData.getString("clientName") ?: ""
        val orderName = inputData.getString("orderName") ?: ""
        val dueDate = inputData.getLong("dueDate", -1L)
        showNotification(client, orderName)
        return Result.success()
    }

    private fun showNotification(client: String, orderName: String) {
        val nm = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "oakay_reminders"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val ch = NotificationChannel(channelId, "Напоминания Oakay", NotificationManager.IMPORTANCE_DEFAULT)
            nm.createNotificationChannel(ch)
        }
        val notif = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Срок сдачи заказа")
            .setContentText("Заказ $client — $orderName скоро должен быть сдан.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
        nm.notify((System.currentTimeMillis()%100000).toInt(), notif)
    }
}
