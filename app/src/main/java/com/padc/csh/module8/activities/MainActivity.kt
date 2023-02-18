package com.padc.csh.module8.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.padc.csh.module8.R
import com.padc.csh.module8.services.DownloadIntentService
import com.padc.csh.module8.services.DownloadJobIntentService
import com.padc.csh.module8.services.ForegroundService
import com.padc.csh.module8.workmanager.DownloadWorker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //service
        btnStartService.setOnClickListener {
            ForegroundService.startService(this,"Start Service")
        }

        //stop service
        btnStopService.setOnClickListener {
            ForegroundService.endService(this)
        }

        //INTENT Service //auto stop
        btnDowndloadImage.setOnClickListener {
            val intent=Intent(this,DownloadIntentService::class.java)
            intent.putExtra("image_path","https://news.cgtn.com/news/7a596a4e7755544d3049444d3259444d776b444f31457a6333566d54/img/dc916c16c1774ea0821ff619bcc95b01/dc916c16c1774ea0821ff619bcc95b01.jpg")
            startService(intent)
        }

        //job intent service//auto stop
        btnDowndloadImageNew.setOnClickListener {
            val intent=Intent(this,DownloadJobIntentService::class.java)
            intent.putExtra("image_path","https://cdn-imgix.headout.com/mircobrands-content/image/5b268eb848662be4d47e7ca16c7e1574-a%20huge%20flower%20bed%20of%20tulips%20at%20keukenhof%20gardens.jpg?auto=compress%2Cformat&h=573&q=75&fit=crop&ar=16%3A9&fm=webp")
            DownloadJobIntentService.startWork(this,intent)

        }

        btnDowndloadImageByWorkder.setOnClickListener {


            //constraints
            val constraints=androidx.work.Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_ROAMING)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build()

            //work request
            val downdloadWorkRequest= OneTimeWorkRequestBuilder<DownloadWorker>()
                .setConstraints(constraints)
                .build()

            //enqueue work manager
            WorkManager.getInstance(applicationContext).enqueue(downdloadWorkRequest)
        }
    }
}