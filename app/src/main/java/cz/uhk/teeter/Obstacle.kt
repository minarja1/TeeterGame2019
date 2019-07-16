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
            ball.position.x = (x - ball.radius).toFloat().pxToMeters
            ball.velocityX = ball.velocityX * (-REFLECTION)
        }

        // right
        else if (ballPosition.x < x2 + ball.radius
            && ballPosition.x > x2 - ball.radius
            && ballPosition.y > y - ball.radius
            && ballPosition.y < y2 + ball.radius
        ) {
            ball.position.x = (x2 + ball.radius).toFloat().pxToMeters
            ball.velocityX = ball.velocityX * (-REFLECTION)
        }

        // top
        else if (ballPosition.x > x - ball.radius
            && ballPosition.x < x2 + ball.radius
            && ballPosition.y > y - ball.radius
            && ballPosition.y < y + ball.radius
        ) {
            ball.position.y = (y - ball.radius).toFloat().pxToMeters
            ball.velocityY = ball.velocityY * (-REFLECTION)
        }

        // bottom
        else if (ballPosition.x > x - ball.radius
            && ballPosition.x < x2 + ball.radius
            && ballPosition.y < y2 + ball.radius
            && ballPosition.y > y2 - ball.radius
        ) {
            ball.position.y = (y2 + ball.radius).toFloat().pxToMeters
            ball.velocityY = ball.velocityY * (-REFLECTION)
        }

    }
}