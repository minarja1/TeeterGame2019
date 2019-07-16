package cz.uhk.teeter

class Obstacle {

    var x = 0
    var y = 0
    var x2 = 0
    var y2 = 0

    val REFLECTION = 0.99f

    fun handleCollisions(ball: Ball) {

        // handling of collisions with ball
        val ballPosition = Point2D().apply {
            x = ball.position.x.metersToPx
            y = ball.position.y.metersToPx
        }

        // left
        if (ballPosition.x > x - ball.radius
            && ballPosition.x < x + ball.radius
            && ballPosition.y > y - ball.radius
            && ballPosition.y < y2 + ball.radius
        ) {
            ball.position.x = (x2 + ball.radius).toFloat().pxToMeters
            ball.velocityX = ball.velocityX * (-REFLECTION)
        }

        // todo right
        // todo top
        // todo bottom

    }
}