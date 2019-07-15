package cz.uhk.teeter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val FPS = 120L
    private lateinit var handler: SensorHandler
    private var init = false
    private lateinit var level: Level

    private val handlerOs = Handler()
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            if (init) {
                draw()
                detectFails()
                detectWin()
            }
            handlerOs.postDelayed(this, 1000 / FPS)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContentView(R.layout.activity_main)


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


        }
        // loading of level in try catch
    }

    override fun onPause() {
        super.onPause()

        // removing of callbacks
    }
}
