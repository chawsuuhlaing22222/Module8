package com.padc.csh.module8.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.padc.csh.module8.R
import com.padc.csh.module8.activities.MainActivity

class ForegroundService: Service() {

    private val OWN_CHANNEL="ID_NOTIFICATION_CHANNEL"

    companion object{
        const val IE_EXTRA="Foreground service"

        fun startService(context: Context,message:String){
            val service=Intent(context,ForegroundService::class.java)
            service.putExtra(IE_EXTRA,message)
            ContextCompat.startForegroundService(context,service)
        }

        fun endService(context: Context){
            val service=Intent(context,ForegroundService::class.java)
            context.stopService(service)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //get input
        val inputString=intent?.getStringExtra(IE_EXTRA)

        //create
        createNotificationChannel()

        //create pending intent
        val intent=Intent(this,MainActivity::class.java)
        val pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE)

        //create notification
        val notification=NotificationCompat.Builder(this,OWN_CHANNEL)
            .setContentTitle("Foreground service")
            .setContentText(inputString)
            .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
            .setContentIntent(pendingIntent)
            .build()

        //start notification
        startForeground(1,notification)
        return START_NOT_STICKY

    }


    private fun createNotificationChannel(){

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val notificationChannel=NotificationChannel(OWN_CHANNEL,"Notification Channel",NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(notificationChannel)
        }



    }

    //this work with bound service
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}