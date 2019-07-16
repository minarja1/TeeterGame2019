package cz.uhk.teeter

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val FPS = 120L
    private val BALL_RADIUS = 20
    private val HOLE_RADIUS = 30

    private lateinit var handler: SensorHandler
    private var init = false
    private lateinit var level: Level
    private var paint = Paint().apply {
        color = Color.BLACK
    }

    private var levelNumber = 1

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
        surfaceView.setOnClickListener {
            handler.unlockBall()
        }

        // sensor handler initialization
    }

    private fun draw() {

        // drawing of ball, obstacles, holes, ...
        val canvas = surfaceView.holder.lockCanvas()

        // clear of canvas
        canvas.drawColor(Color.WHITE)

        // obstacles
        paint.setColor(Color.BLACK)
        for (obstacle in level.obstacles) {
            canvas.drawRect(
                obstacle.x.toFloat(),
                obstacle.y.toFloat(),
                obstacle.x2.toFloat(),
                obstacle.y2.toFloat(),
                paint
            )
        }

        //holes
        for (hole in level.holes) {
            canvas.drawCircle(
                hole.positionInMeters.x,
                hole.positionInMeters.y,
                HOLE_RADIUS.toFloat(),
                paint
            )
        }

        //startingPoint
        paint.setColor(Color.GREEN)
        canvas.drawCircle(
            level.startingPosition.x,
            level.startingPosition.y,
            HOLE_RADIUS.toFloat(),
            paint
        )

        //endingPoint
        paint.setColor(Color.RED)
        canvas.drawCircle(
            level.endPosition.x,
            level.endPosition.y,
            HOLE_RADIUS.toFloat(),
            paint
        )

        // ball
        paint.setColor(Color.GRAY)
        canvas.drawCircle(
            handler.ball.position.x.metersToPx,
            handler.ball.position.y.metersToPx,
            handler.ball.radius.toFloat(), paint
        )

        surfaceView.holder.unlockCanvasAndPost(canvas)

    }

    private fun detectWin() {
        val position = Point2D().apply {
            x = handler.ball.position.x.metersToPx
            y = handler.ball.position.y.metersToPx
        }

        if (Math.sqrt(
                (Math.pow(level.endPosition.x.toDouble() - position.x, 2.0)) +
                        (Math.pow(level.endPosition.y.toDouble() - position.y, 2.0))
            ) < HOLE_RADIUS + BALL_RADIUS
        ) {
            handler.lockBall()
            if (levelNumber < 5) {
                levelNumber++
                level = Level.loadFromAssets(
                    this@MainActivity,
                    "level_$levelNumber.txt",
                    surfaceView.width, surfaceView.height
                )
                handler.init(surfaceView, level, handler.ball)
                handler.resetBall()
            } else {
                Toast.makeText(this, "You win!!!", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun detectFails() {

        val position = Point2D().apply {
            x = handler.ball.position.x.metersToPx
            y = handler.ball.position.y.metersToPx
        }

        for (hole in level.holes) {
            // c = sqrt((x2-x1)^2 + (y2-y1)^2)
            if (Math.sqrt(
                    (Math.pow(hole.positionInMeters.x.toDouble() - position.x, 2.0)) +
                            (Math.pow(hole.positionInMeters.y.toDouble() - position.y, 2.0))
                ) < HOLE_RADIUS + BALL_RADIUS
            ) {
                handler.lockBall()
                handler.resetBall()
                break
            }
        }
    }

    override fun onResume() {
        super.onResume()

        surfaceView.post {

            level = Level.loadFromAssets(
                this@MainActivity,
                "level_$levelNumber.txt",
                surfaceView.width, surfaceView.height
            )

            // later level init
            val ball = Ball()
            ball.radius = BALL_RADIUS
            ball.position = Point2D()
            ball.position.x = surfaceView.width / 2f
            ball.position.y = surfaceView.height / 2f


            handler = SensorHandler()
            handler.init(surfaceView, level, ball)
            init = true
        }

        handlerOs.postDelayed(runnable, 1000 / FPS)
        // loading of level in try catch
    }

    override fun onPause() {
        handlerOs.removeCallbacks(runnable)
        init = false

        super.onPause()

        // removing of callbacks
    }
}
