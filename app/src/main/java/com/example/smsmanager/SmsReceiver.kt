package com.example.smsmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlin.math.log

class SmsReceiver() : BroadcastReceiver() {
    companion object {
        var smsNumber = mutableStateOf("")
    }

    override fun onReceive(context: Context, intent: Intent) {
        // Define smsNumber here
        Log.e("1212", "onReceive: ")
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            Log.e("1212", "onReceive: ")
            val bundle = intent.extras
            if (bundle != null) {
                Log.e("1212", "onReceive: ")
                val pdus = bundle["pdus"] as Array<*>?
                if (pdus != null) {
                    Log.e("1212", "onReceive: ")
                    for (i in pdus.indices) {
                        val pdu = pdus[i] as ByteArray
                        val message = SmsMessage.createFromPdu(pdu)
                        val sender = message.originatingAddress
                        val messageBody = message.messageBody
                        Log.e("1212", "onReceive: $smsNumber")
                        //smsNumber.add("From: $sender - Message: $messageBody")
                        if (sender != null) {
                            Log.e("1212", "onReceive: sender: $sender", )
                            if (sender == smsNumber.value) {
                                sendNotification(context,messageBody,sender)
                            }
                        }

                    }
                }
            }
        }
    }
}


// 5. Send notifications
private fun sendNotification(context: Context, message: String, title: String) {
    val notificationManager = ContextCompat.getSystemService(
        context,
        NotificationManager::class.java
    ) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "CHANNEL_ID",
            "SMS Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, "CHANNEL_ID")
        .setContentTitle(title)
        .setContentText(message)
        .setSmallIcon(R.drawable.baseline_notifications_24)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    notificationManager.notify(101, notification)
}