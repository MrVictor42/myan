package com.victor.myan.screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ProcessLifecycleOwner
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseLayout
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ObserverNotification

class FirstScreenActivity : AppCompatActivity() {

    private val auxFunctionsHelper = AuxFunctionsHelper()
    private val channelID = "channel_id_example_01"
    private val notificationID = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_screen_activity)

        val btn = findViewById<AppCompatButton>(R.id.teste)

        if(supportActionBar != null) {
            supportActionBar!!.hide()
        }
        window.statusBarColor =  ContextCompat.getColor(this, R.color.black)

        createNotification()
        btn.setOnClickListener {
            sendNotification()
        }
        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(ObserverNotification())

//        Handler(Looper.getMainLooper()).postDelayed({
//            if(auxFunctionsHelper.userHasConnection(this)) {
//                if(auxFunctionsHelper.userIsAuthenticated()) {
//                    val intent = Intent(this, BaseLayout::class.java)
//                    startActivity(intent)
//                    finish()
//                } else {
//                    val intent = Intent(this, PresentationActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            } else {
//                val intent = Intent(this, WithoutConnectionActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }, 2000)
    }

    private fun createNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification name"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val intent = Intent(this, BaseLayout::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo)

        val builder = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.drawable.chapeu)
            .setContentTitle("Example title")
            .setContentText("Example description")
            .setLargeIcon(bitmapLargeIcon)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            notify(notificationID, builder.build())
        }
    }
}