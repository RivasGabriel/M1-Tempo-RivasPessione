package com.example.tempoapp.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.example.tempoapp.R
import com.example.tempoapp.model.TempoDay
import com.example.tempoapp.ui.MainActivity

object TempoNotification {

    private const val NOTIFICATION_ID = 0
    private const val NOTIFICATION_CHANNEL_ID = "TempoAppChannel"

    fun showNotification(context: Context, tempoDay: TempoDay) {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the content intent for the notification, which launches
        // the MainActivity
        val contentIntent = Intent(context, MainActivity::class.java)

        // Create a PendingIntent from the content intent to launch the
        // MainActivity with the notification
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(
                context.getString(
                    R.string.notification_text,
                    tempoDay.date,
                    tempoDay.color.name.toLowerCase().capitalize()
                )
            )
            .setColor(getColorFromTempoColor(tempoDay.color))
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun getColorFromTempoColor(tempoColor: TempoDay.TempoColor): Int {
        return when (tempoColor) {
            TempoDay.TempoColor.BLUE -> Color.BLUE
            TempoDay.TempoColor.WHITE -> Color.WHITE
            TempoDay.TempoColor.RED -> Color.RED
        }
    }
}
