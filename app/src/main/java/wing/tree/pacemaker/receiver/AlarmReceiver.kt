package wing.tree.pacemaker.receiver

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import wing.tree.pacemaker.R
import wing.tree.pacemaker.domain.constant.ZERO
import wing.tree.pacemaker.extension.checkPermission
import wing.tree.pacemaker.model.Instance
import wing.tree.pacemaker.view.MainActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.setExtrasClassLoader(Instance::class.java.classLoader) ?: return

        val instance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Instance.EXTRA_INSTANCE, Instance::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(Instance.EXTRA_INSTANCE)
        } ?: return

        context?.let {
            postNotification(it, instance)
        }
    }

    private fun postNotification(context: Context, instance: Instance) {
        val notificationManager = NotificationManagerCompat.from(context)
        val channelId = "instance.channel"
        val notificationId = instance.notificationId
        val name = instance.title
        val importance = NotificationManager.IMPORTANCE_MIN
        val notificationChannel = NotificationChannel(channelId, name, importance)

        notificationChannel.setShowBadge(false)
        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.cancel(notificationId)

        val pendingIntent = PendingIntent.getActivity(
            context,
            ZERO,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setShowWhen(false)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(instance.title)
            .setContentText(instance.description)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setAutoCancel(true)
            .build()

        if (context.checkPermission(Manifest.permission.POST_NOTIFICATIONS)) {
            notificationManager.notify(notificationId, notification)
        }
    }
}
