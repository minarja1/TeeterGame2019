package cz.uhk.teeter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.view.SurfaceView

class SensorHandler : SensorEventListener {

    override fun onSensorChanged(p0: SensorEvent?) {

        // calculation of velocity, acceleration and ball position
    }

    fun init(surfaceView: SurfaceView, level: Level, ball: Ball) {

        // initialization of a game
    }

    private fun clear() {

        // removing of noise from low pass filter
    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // ignored?
    }
}