package com.example.servicesampling

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.Looper

class CounterService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private var seconds = 0;


    override fun onBind(intent: Intent?): CounterServiceBinder? {
        intent?.let {
            val value = it.getIntExtra("seconds", 0)
            seconds = value
            setCounter()
        }
        return CounterServiceBinder()
    }

    private fun setCounter() {
        (application as BaseApplication).set(++seconds)
        handler.postDelayed(::setCounter, 1000)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        handler.removeCallbacksAndMessages(null)
        return super.onUnbind(intent)
    }

    fun getSeconds() : Int {
        return seconds
    }

    inner class CounterServiceBinder : Binder() {
        fun getService(): CounterService {
            return this@CounterService
        }
    }
}