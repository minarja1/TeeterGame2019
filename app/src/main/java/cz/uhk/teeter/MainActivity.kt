package cz.uhk.teeter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // todo
        // fullscreen
        // setting of surface view
        // sensor handler initialization
    }

    private fun draw() {

        // drawing of ball, obstacles, holes, ...
    }

    private fun detectWin() {
        // todo
    }

    private fun detectFails() {
        // todo
    }

    override fun onResume() {
        super.onResume()

        surfaceView.post {
            // later level init

            SensorHandler().init(surfaceView, Level(), Ball())
        }
        // loading of level in try catch
    }

    override fun onPause() {
        super.onPause()

        // removing of callbacks
    }
}
