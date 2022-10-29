package com.example.servicesampling

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.servicesampling.databinding.ActivityMainBinding

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
        binding.tvSeconds.text = seconds++.toString()
        // add Counter handler infinite
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
        // remove Counter handler event
        handler.removeCallbacksAndMessages(null)
    }

    // foreground -> background(isRun flag로 count event 실행 중인지 확인)
    override fun onPause() {
        super.onPause()
        if(isRun) {
            stopCounter()
            runService()
        }
    }

    // background로 foreground로 돌아올 때,
    // 최초 Activity 진입시에도 onResume이 호출되기에
    // isRun 플래그로 background -> foreground인지 체크
    override fun onResume() {
        super.onResume()
        if(!isRun) {
            startCounter()
            stopService(Intent(this, CounterService::class.java))
        }
    }


    private fun runService() {
        // background -> foreground = service에서 activity로 돌아올 때,
        // service의 seconds를 콜백받기 위한 broadcast listener
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

        // foreground -> background = activity에서 service로 진입할 때,
        // activity의 seconds를 service로 보내기
        val intent = Intent(this, CounterService::class.java)
        intent.putExtra("seconds", seconds)
        startService(intent)
    }

}