package com.padc.csh.module8.workmanager

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File

class DownloadWorker(val context:Context,workerParameters: WorkerParameters)
    :Worker(context,workerParameters) {

    override fun doWork(): Result {

        try {
            val fileName:String="${System.currentTimeMillis()}.jpg"
            val request=
                DownloadManager.Request(Uri.parse("https://news.cgtn.com/news/7a596a4e7755544d3049444d3259444d776b444f31457a6333566d54/img/dc916c16c1774ea0821ff619bcc95b01/dc916c16c1774ea0821ff619bcc95b01.jpg"))
            request.apply {
                setTitle("Download Image")
                setDescription("Downloading")
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, File.separator+fileName)

                val downloadManager=context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                downloadManager.enqueue(request)
            }


            return Result.success()
        }catch (e:Exception){
            return Result.failure()
        }
    }
}