import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.tempo.R
import com.example.tempo.data.model.TempoColor
import com.example.tempo.ui.MainActivity

object NotificationUtils {

    private const val TEMPO_NOTIFICATION_CHANNEL_ID = "tempo_notification_channel"
    private const val TEMPO_NOTIFICATION_CHANNEL_NAME = "Tempo Notification Channel"

    /**
     * Create and show a notification for the given tempo color.
     *
     * @param context the context
     * @param tempoColor the tempo color
     */
    fun showTempoNotification(context: Context, tempoColor: TempoColor) {
        // Create a notification builder
        val builder = NotificationCompat.Builder(context, TEMPO_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_tempo_notification)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text, tempoColor.name))
            .setAutoCancel(true)

        // Create a pending intent for the main activity
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(pendingIntent)

        // Show the notification
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())
    }

    /**
     * Create the tempo notification channel.
     *
     * @param context the context
     */
    fun createTempoNotificationChannel(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the notification channel
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationCompat.Channel(TEMPO_NOTIFICATION_CHANNEL_ID, TEMPO_NOTIFICATION_CHANNEL_NAME, importance)
        channel.description = context.getString(R.string.notification_channel_description)
        notificationManager.createNotificationChannel(channel)
    }
}
