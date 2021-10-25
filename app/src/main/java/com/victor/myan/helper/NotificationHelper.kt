package com.victor.myan.helper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseLayout

class NotificationHelper(context: Context) : LifecycleObserver {

    private val channelID = "myAn_01"
    private val contextNotification = context
    private val notificationID = 42

    fun registerLifecycle(lifecycle : Lifecycle){
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        // Aqui vamos tentar depois colocar para já iniciar o animes da proxima tela
        Log.e("FOREGROUND", "ESTAMOS DE FOREGROUND")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification name"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager : NotificationManager = contextNotification.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            val intent = Intent(contextNotification, BaseLayout::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent : PendingIntent = PendingIntent.getActivity(contextNotification, 0, intent, 0)
            val bitmap = BitmapFactory.decodeResource(contextNotification.resources, R.drawable.logo)
            val bitmapLargeIcon = BitmapFactory.decodeResource(contextNotification.resources, R.drawable.logo)

            val builder = NotificationCompat.Builder(contextNotification, channelID)
                .setSmallIcon(R.drawable.chapeu)
                .setContentTitle("Example title")
                .setContentText("Example description")
                .setLargeIcon(bitmapLargeIcon)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            with(NotificationManagerCompat.from(contextNotification)) {
                notify(notificationID, builder.build())
            }
        }
    }
}