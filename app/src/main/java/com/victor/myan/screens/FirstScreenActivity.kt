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
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.victor.myan.R
import com.victor.myan.baseFragments.BaseLayout
import com.victor.myan.helper.AuxFunctionsHelper
import com.victor.myan.helper.ScreenStateHelper
import com.victor.myan.model.Anime
import com.victor.myan.model.PersonalList
import com.victor.myan.viewmodel.PersonalListViewModel

class FirstScreenActivity : AppCompatActivity(), LifecycleObserver {

    private val auxFunctionsHelper = AuxFunctionsHelper()
    private val personalList by lazy {
        ViewModelProvider(this).get(PersonalListViewModel::class.java)
    }
    private val channelID = "myAn_01"
    private val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
    private val userRef = FirebaseDatabase.getInstance().getReference("users").orderByChild("userID").equalTo(currentUser)
    private val listRef = userRef.ref.child(currentUser).child("list").orderByChild("userID").equalTo(currentUser)
    private val listID : MutableList<String> = mutableListOf()
    private val animeListLiveData : MutableLiveData<ScreenStateHelper<List<Anime>?>> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_screen_activity)

        if(supportActionBar != null) {
            supportActionBar!!.hide()
        }
        window.statusBarColor =  ContextCompat.getColor(this, R.color.black)

        personalList.getPersonalList()
        personalList.personalList.observe(this, { personalList ->
            when(personalList) {
                is ScreenStateHelper.Success -> {
                    onBackground(personalList.data)
                } else -> {
                    // User doesn't has personal list yet
                }
            }
        })

        Handler(Looper.getMainLooper()).postDelayed({
            if(auxFunctionsHelper.userHasConnection(this)) {
                if(auxFunctionsHelper.userIsAuthenticated()) {
                    val intent = Intent(this, BaseLayout::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, PresentationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                val intent = Intent(this, WithoutConnectionActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        // Aqui vamos tentar depois colocar para já iniciar o animes da proxima tela
        Log.e("FOREGROUND", "ESTAMOS DE FOREGROUND")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground(personalList: List<PersonalList>?) {
        val animeList : MutableList<Anime> = arrayListOf()
        for(aux in personalList!!.indices) {
            listID.add(personalList[aux].ID)
        }
        for(aux in 0 until listID.size) {
            val listRef = listRef.ref.child(listID[aux])
            val animeRef = listRef.ref.child("anime")

            animeRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (result != null) {
                        if (result.exists()) {
                            result.let {
                                result.children.map { snapshot ->
                                    animeList.add(snapshot.getValue(Anime::class.java)!!)
                                }
                            }
                            animeListLiveData.postValue(ScreenStateHelper.Success(animeList))
                        }
                    }
                }
            }
        }
        animeListLiveData.observe(this, { anime ->
            when(anime) {
                is ScreenStateHelper.Success -> {
                    with(anime.data) { val animeResult = this!!
                        for(aux in animeResult.indices) {
                            if(animeResult[aux].status == "Currently Airing") {
                                val regexBroadcast = animeResult[aux].broadcast.split("\\s".toRegex())[0]
                                if(regexBroadcast == auxFunctionsHelper.getCurrentDays()) {
                                    Log.e("anime", animeResult[aux].title)
                                    createNotification(animeResult[aux])
                                    sendNotification(animeResult[aux], aux)
                                }
                            }
                        }
                    }
                }
                else -> {

                }
            }
        })
    }

    private fun createNotification(anime: Anime) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Today is day of ${ anime.title }!"
            val descriptionText = "Click here and see!"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(anime: Anime, aux: Int) {
        val intent = Intent(this, BaseLayout::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.logo)

        val builder = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.drawable.chapeu)
            .setContentTitle("Today is day of ${ anime.title }!")
            .setContentText("Click here and see!")
            .setLargeIcon(bitmapLargeIcon)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setGroup(channelID)
        with(NotificationManagerCompat.from(this)) {
            notify(aux, builder.build())
        }
    }
}