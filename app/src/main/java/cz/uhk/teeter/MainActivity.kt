package cz.uhk.teeter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // todo
        // setting of surface view, fullscreen
        // sensor handler
    }

    private fun draw() {

        // drawing of ball, obstacles, holes, ...
    }

    private fun detectWin() {

        // detection of win
    }

    private fun detectFails() {

        // detection of fail
    }

    override fun onResume() {
        super.onResume()

        // loading of level in try catch
    }

    override fun onPause() {
        super.onPause()
        // removing of callbacks
    }
}
