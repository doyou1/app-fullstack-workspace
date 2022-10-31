package com.example.servicesampling

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.servicesampling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var seconds = 0
    private val handler = Handler(Looper.getMainLooper())
    private var isRun = true

    private var counterService: CounterService? = null

    private val conn = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder?) {
            counterService = (binder as CounterService.CounterServiceBinder)?.getService()
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
        }
    }

    private lateinit var _application: BaseApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        _application = application as BaseApplication
        init()
    }

    private fun init() {
        initCounter()
    }

    private fun initCounter() {
        isRun = true
        seconds = _application.get()
        setCounter()
    }

        private fun setCounter() {
            _application.set(++seconds)
            binding.tvSeconds.text = seconds.toString()
            // add Counter handler infinite
            handler.postDelayed(::setCounter, 1000)
        }

    private fun startCounter() {
        isRun = true
        setCounter()
    }

    private fun stopCounter() {
        isRun = false

        // remove Counter handler event
        handler.removeCallbacksAndMessages(null)
    }

    // foreground -> background(isRun flag로 count event 실행 중인지 확인)
    override fun onPause() {
        super.onPause()
        if(isRun) {
            stopCounter()
            val intent = Intent(this, CounterService::class.java)
            intent.putExtra("seconds", seconds)
            bindService(intent, conn, Context.BIND_AUTO_CREATE)
        }
    }

    // background로 foreground로 돌아올 때,
    // 최초 Activity 진입시에도 onResume이 호출되기에
    // isRun 플래그로 background -> foreground인지 체크
    override fun onResume() {
        super.onResume()
        if(!isRun) {
            unbindService(conn)
            counterService?.let {
                seconds =  it.getSeconds()
            }
            startCounter()
        }
    }

    override fun onStop() {
        stopCounter()
        super.onStop()
    }

    override fun onDestroy() {
        unbindService(conn)
        super.onDestroy()
    }
}