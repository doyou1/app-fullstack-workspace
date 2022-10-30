package com.example.pedometer_app

import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class PedometerService : Service(), SensorEventListener {

    private var step = 0;
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor

    override fun onCreate() {
        super.onCreate()
        initStepEvent()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRun = true;

        val step = intent?.getIntExtra("step", -1)
        if (step != null) {
            this.step = step
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun initStepEvent() {
        // Create Sensor Manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        // Step Detect Sensor
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

    }


    override fun onDestroy() {
        super.onDestroy()

        val intent = Intent("PedometerService")
        intent.putExtra("step", step);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        isRun = false;
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.sensor?.let {
            if(it.type == Sensor.TYPE_STEP_DETECTOR && event.values[0] == 1.0f) {
                step += 1
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return PedometerServiceBinder();
    }

    inner class PedometerServiceBinder : Binder() {
        fun getService(): PedometerService {
            return this@PedometerService
        }
    }

    companion object {
        var isRun = false;
    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

}