package com.example.servicesampling

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.servicesampling.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var seconds = 0
    private val handler = Handler(Looper.getMainLooper())
    private var isRun = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        setClickEvent()
        setCounter()
    }

    private fun setClickEvent() {
        binding.btn.setOnClickListener {
            val btnText = binding.btn.text

            // Counter Running
            if(btnText.equals("Stop")) stopCounter()
            // Counter Stopped
            else startCounter()
        }
    }

    private fun setCounter() {
        seconds += 1
        binding.tvSeconds.text = seconds.toString()
        handler.postDelayed(::setCounter, 1000)
    }

    private fun startCounter() {
        binding.btn.text = "Stop"
        isRun = true
        setCounter()
    }

    private fun stopCounter() {
        binding.btn.text = "Start"
        isRun = false
        handler.removeCallbacksAndMessages(null)
    }

    override fun onResume() {
        super.onResume()
        if(!isRun) {
            startCounter()
            stopService(Intent(this, CounterService::class.java))
        }
    }

    override fun onPause() {
        super.onPause()
        Log.e("onPause", "onPause")
        if(isRun) {
            stopCounter()
            runService()
        }
    }

    private fun runService() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
            object: BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val seconds = intent?.getIntExtra("seconds", -1)
                    if (seconds != null) {
                        this@MainActivity.seconds = seconds
                    }
                }
            }
        , IntentFilter("CounterService"))

        val intent = Intent(this, CounterService::class.java)
        intent.putExtra("seconds", seconds)
        startService(intent)
    }
}