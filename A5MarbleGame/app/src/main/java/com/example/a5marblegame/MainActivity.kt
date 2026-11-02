package com.example.a5marblegame

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import com.example.a5marblegame.ui.theme.A5MarbleGameTheme

//The activity listens to the sensor and passes readings to the VM

class MainActivity : ComponentActivity(), SensorEventListener {

    private val viewModel: MarbleViewModel by viewModels()

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    // used only if the phone has no gravity sensor
    private var usingAccelerometer = false
    private val filtered = FloatArray(3)
    private var filterStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // gets the system sensor service and looks up the gravity sensor
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // falls back to the accelerometer if there's no gravity sensor
        val gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        sensor = gravitySensor ?: sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        usingAccelerometer = gravitySensor == null

        val label = when {
            sensor == null -> "none found"
            usingAccelerometer -> "Accelerometer (filtered)"
            else -> "Gravity"
        }

        // show marble screen
        setContent {
            A5MarbleGameTheme {
                if (sensor == null) {
                    Text("This device has no gravity sensor or accelerometer.")
                } else {
                    MarbleScreen(viewModel, label)
                }
            }
        }
    }

    // start listening when the app is visible
    override fun onResume() {
        super.onResume()
        sensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    // stop listening when the app goes to the background to save battery
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        viewModel.resetClock()
        filterStarted = false
    }

    // called every time the sensor has a new reading
    override fun onSensorChanged(event: SensorEvent) {
        var gravityX = event.values[0]
        var gravityY = event.values[1]

        // filter to smooth out accelerometer
        if (usingAccelerometer) {
            if (!filterStarted) {
                event.values.copyInto(filtered, endIndex = 3)
                filterStarted = true
            } else {
                for (i in 0..2) {
                    filtered[i] = ALPHA * filtered[i] + (1 - ALPHA) * event.values[i]
                }
            }
            gravityX = filtered[0]
            gravityY = filtered[1]
        }

        viewModel.onGravity(gravityX, gravityY, event.timestamp)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    companion object {
        private const val ALPHA = 0.8f
    }
}