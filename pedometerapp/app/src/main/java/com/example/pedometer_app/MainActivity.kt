package com.example.pedometer_app

import android.Manifest
import android.app.ActivityManager
import android.content.*
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.pedometer_app.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityMainBinding
    private var isGranted = false

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            application.onTerminate()
        }
        this@MainActivity.isGranted = true
    }

    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private var isRun = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
//        reset()
        initStepEvent()
        checkActivityPermission()
        setStepFromSharedPreferences()
    }

    private fun reset() {
        val prefs = getSharedPreferences("step", Context.MODE_PRIVATE)
        prefs.edit().putString("log", "empty").putInt("value", 0).apply()
    }

    private fun initStepEvent() {
        // Create Sensor Manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        // Step Detect Sensor
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkActivityPermission() {
        val activityPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
        if(activityPermission == PackageManager.PERMISSION_DENIED) {
            launcher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
        }
    }

    private fun setStepFromSharedPreferences() {
        val prefs = getSharedPreferences("step", Context.MODE_PRIVATE)
        val step = prefs.getInt("value", -1);

        if(step != -1) {
            binding.step = step
        }
    }

    override fun onPause() {
        super.onPause()
        if(isRun) {
            sensorManager.unregisterListener(this)
            runService()
            isRun = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        val prefs = getSharedPreferences("step", Context.MODE_PRIVATE)
        val log = prefs.getString("log", "empty");
        Log.e("LOG", "log: $log")

        super.onResume()

        if(PedometerService.isRun) {
            stopService(Intent(this, PedometerService::class.java))
        }
        if(!isRun) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            isRun = true
        }
    }

    private fun runService() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
            object: BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val step = intent?.getIntExtra("step", -1)
                    if (step != null) {
                        binding.step = step
                    }
                }
            }
            , IntentFilter("PedometerService"))

        val intent = Intent(this, PedometerService::class.java)
        intent.putExtra("step", binding.step)
        startService(intent)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.sensor?.let {
            if(it.type == Sensor.TYPE_STEP_DETECTOR && event.values[0] == 1.0f) {
                binding.step += 1

                saveStepLog()
            }
        }
    }

    private fun saveStepLog() {
        val prefs = getSharedPreferences("step", Context.MODE_PRIVATE)
        val log = prefs.getString("log", "empty");
        val editor = prefs.edit()
        val strNow = DateUtil.getStrNow()

        if(log.equals("empty")) {
            editor.putString("log", "$strNow\n")
        } else {
            editor.putString("log", "$log$strNow\n")
        }

        editor.apply()
    }

    private fun saveStepInSharedPreferences() {
        val prefs = getSharedPreferences("step", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putInt("value", binding.step);
        editor.apply()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onDestroy() {
        saveStepInSharedPreferences()
        super.onDestroy()
    }

}