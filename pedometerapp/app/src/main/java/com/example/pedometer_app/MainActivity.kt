package com.example.pedometer_app

import android.Manifest
import android.app.ActivityManager
import android.content.*
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.pedometer_app.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG: String = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkActivityPermission()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkActivityPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
        }
    }

    override fun onResume() {
        super.onResume()
        resetPedometerService()
        resetSharedPreferencesChangeListener()
    }

    private fun resetPedometerService() {
        val intent = Intent(this, PedometerService::class.java)
        stopService(intent)
        startService(intent)
    }

    private fun resetSharedPreferencesChangeListener() {
        handler.removeCallbacksAndMessages(null)
        setStepInfinite()
    }

    private fun setStepInfinite() {
        val prefs = getSharedPreferences("step", Context.MODE_PRIVATE)
        binding.step = prefs.getInt("value", 0)

        // 5초마다
        handler.postDelayed(::setStepInfinite, 1000 * 5)
    }


}