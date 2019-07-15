package cz.uhk.teeter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.SurfaceView

class SensorHandler : SensorEventListener {

    override fun onSensorChanged(event: SensorEvent?) {

        // calculation of velocity, acceleration and ball position

        val x = event?.values?.get(0)
        val y = event?.values?.get(1)
        val z = event?.values?.get(2)

        val format = "%.4f"

        println("X: " + format.format(x) +
                ", Y: " + format.format(y) +
                ", Z: " + format.format(z))

        // X: 0.3456, Y: 3.3456, Z: 6.5433

    }

    fun init(surfaceView: SurfaceView, level: Level, ball: Ball) {

        // initialization of a game

        val manager = surfaceView.context
            .getSystemService(Context.SENSOR_SERVICE) as SensorManager

        manager.registerListener(this,
            manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_FASTEST)

    }

    private fun clear() {

        // removing of noise from low pass filter
    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // ignored?
    }
}