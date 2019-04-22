package com.nerdery.slicesapp

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri

class ToggleNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_TOGGLE_NOTIFICATION) {
            NotificationSettings.toggleNotification(context)
        }
    }

    companion object {
        const val ACTION_TOGGLE_NOTIFICATION = "com.nerdery.slicesapp.ACTION_TOGGLE_NOTIFICATION"
        val contentProviderUri = Uri.parse("content://com.nerdery.slicesapp/bigslice")
        fun createToggleNotificationPendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, ToggleNotificationReceiver::class.java)
            intent.action = ACTION_TOGGLE_NOTIFICATION
            return PendingIntent.getBroadcast(context, 0, intent, 0)
        }
    }
}
