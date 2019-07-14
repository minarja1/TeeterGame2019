package cz.uhk.teeter

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class SensorHandler: SensorEventListener {

    override fun onSensorChanged(p0: SensorEvent?) {

        // calculation of velocity, acceleration and ball position
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // ignored?
    }
}