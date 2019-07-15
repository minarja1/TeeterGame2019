package cz.uhk.teeter

import android.content.Context
import android.content.res.Resources
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.Surface
import android.view.SurfaceView
import android.view.WindowManager

class SensorHandler : SensorEventListener {

    lateinit var ball: Ball

    val FRICTION = 0.95f
    val REFLECTION = 0.99f

    val GRAVITY = 9.8f
    val NOISE = 0.35f

    val ALPHA = 0.8f
    val gravity = floatArrayOf(0f, 0f, 0f)

    var lastMillis = System.currentTimeMillis()

    var orientation: Int = 0
    var density: Int = 0

    // for detection of collisions with sides of screen
    var width = 0F
    var height = 0F

    override fun onSensorChanged(event: SensorEvent?) {

        // TODO if (ballLocked)

        val nowMillis = System.currentTimeMillis()

        event?.values?.run {

            // this is how to get rid of the noise
            val clearValues = floatArrayOf(
                clear(this[0]),
                clear(this[1]),
                clear(this[2])
            )

            // low pass filter
            // we will use 80% of the old values
            gravity[0] = ALPHA * gravity[0] + (1 - ALPHA) * clearValues[0]
            gravity[1] = ALPHA * gravity[1] + (1 - ALPHA) * clearValues[1]
            gravity[2] = ALPHA * gravity[2] + (1 - ALPHA) * clearValues[2]

            // normalization (wtf?)
            // gravity[0] = -1.2, gravity[1] = 0.8, gravity[2] = 7.3
            // gravity[0] + gravity[1] + gravity[2] = 1.

            val sum = (Math.abs(gravity[0])+Math.abs(gravity[1])+Math.abs(gravity[2]))

            // changing values to have 9.8 in total x+y+z
            gravity[0] = (gravity[0] / sum * GRAVITY)
            gravity[1] = (gravity[1] / sum * GRAVITY)
            gravity[2] = (gravity[2] / sum * GRAVITY)

            // getting delta of passed time
            val deltaTime = (nowMillis - lastMillis).toFloat() / 1000F

            // getting the new velocity of ball from values
            // v = a*t    (v = velocity, a = acceleration, t = time)
            val newVelocityX = gravity[0] * deltaTime
            val newVelocityY = gravity[1] * deltaTime

            var nowFriction = FRICTION
            if (gravity[0] > -NOISE && gravity[0] < NOISE
                && gravity[1] > -NOISE && gravity[1] < NOISE
            ) {
                nowFriction = 0.97f
            } else {
                nowFriction = FRICTION
            }

            var xValue = 0f
            var yValue = 0f
            when (orientation) {
                Surface.ROTATION_0 -> {
                    xValue = newVelocityX
                    yValue = newVelocityY
                }
                Surface.ROTATION_90 -> {
                    xValue = -newVelocityY
                    yValue = newVelocityX

                }
                Surface.ROTATION_180 -> {
                    xValue = -newVelocityX
                    yValue = -newVelocityY
                }
                else -> {
                    xValue = newVelocityY
                    yValue = -newVelocityX
                }
            }

            //v = v0 + a * t
            var velX = ball.velocityX * nowFriction + xValue
            var velY = ball.velocityY * nowFriction + yValue

            ball.velocityX = ((ALPHA * ball.velocityX) + (1 - ALPHA) * velX)
            ball.velocityY = ((ALPHA * ball.velocityY) + (1 - ALPHA) * velY)

            lastMillis = nowMillis

            ball.position.x = ball.position.x - velX * deltaTime
            ball.position.y = ball.position.y + velY * deltaTime


            // TODO handle collisions with obstacles

        }

    }

    fun init(surfaceView: SurfaceView, level: Level, ball: Ball) {

        val windowManager = surfaceView.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        orientation = windowManager.defaultDisplay.rotation

        density = Resources.getSystem().displayMetrics.densityDpi

        this.ball = ball

        // initialization of a game

        val manager = surfaceView.context
            .getSystemService(Context.SENSOR_SERVICE) as SensorManager

        manager.registerListener(
            this,
            manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_FASTEST
        )

        this.ball.position.x = this.ball.position.x.pxToMeters
        this.ball.position.y = this.ball.position.y.pxToMeters

        width = (surfaceView.width - ball.radius).toFloat().pxToMeters
        height = (surfaceView.height - ball.radius).toFloat().pxToMeters

    }

    private fun clear(value: Float): Float {
        // removing of noise from low pass filter
        return when {
            value < NOISE && value > -NOISE -> 0F
            else -> value
        }
    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // ignored?
    }
}