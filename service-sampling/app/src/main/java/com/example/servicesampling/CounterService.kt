package com.example.servicesampling

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class CounterService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private var seconds = 0;

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val seconds = intent?.getIntExtra("seconds", -1)
        if (seconds != null) {
            this.seconds = seconds
            setCounter()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun setCounter() {
        Log.e("setCounter", "setCounter")
        this.seconds += 1
        handler.postDelayed(::setCounter, 1000)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return CounterServiceBinder()
    }

    override fun onDestroy() {
        super.onDestroy()

        val intent = Intent("CounterService")
        intent.putExtra("seconds", seconds);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    inner class CounterServiceBinder : Binder() {
        fun getService(): CounterService {
            return this@CounterService
        }
    }
}