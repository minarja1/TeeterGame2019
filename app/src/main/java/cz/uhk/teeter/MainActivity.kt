package cz.uhk.teeter

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val FPS = 120L
    private val BALL_RADIUS = 20
    private lateinit var handler: SensorHandler
    private var init = false
    private lateinit var level: Level
    private var paint = Paint().apply {
        color = Color.BLACK
    }

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

        window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContentView(R.layout.activity_main)


        // setting of surface view
        // sensor handler initialization
    }

    private fun draw() {

        // drawing of ball, obstacles, holes, ...
        val canvas = surfaceView.holder.lockCanvas()

        canvas.drawColor(Color.WHITE)

        canvas.drawCircle(handler.ball.position.x.toFloat(),
            handler.ball.position.y.toFloat(),
            handler.ball.radius.toFloat(), paint)

        surfaceView.holder.unlockCanvasAndPost(canvas)

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
            val ball = Ball()
            ball.radius = BALL_RADIUS
            ball.position = Point2D()
            ball.position.x = surfaceView.width/2
            ball.position.y = surfaceView.height/2

            handler = SensorHandler()
            handler.init(surfaceView, Level(), ball)
            init = true
        }

        handlerOs.postDelayed(runnable, 1000/FPS)
        // loading of level in try catch
    }

    override fun onPause() {
        handlerOs.removeCallbacks(runnable)
        init = false

        super.onPause()

        // removing of callbacks
    }
}
