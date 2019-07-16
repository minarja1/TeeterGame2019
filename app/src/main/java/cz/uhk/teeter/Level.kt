package cz.uhk.teeter

import android.content.Context
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

class Level {

    var width = 0
    var height = 0

    lateinit var startingPosition: Point2D
    lateinit var endPosition: Point2D

    var obstacles = mutableListOf<Obstacle>()
    var holes = mutableListOf<Hole>()

    companion object {

        fun loadFromAssets(
            context: Context,
            assetFileName: String,
            width: Int, height: Int
        ): Level {

            val reader = BufferedReader(
                InputStreamReader(
                    context.assets.open(assetFileName)
                )
            )

            val gson = Gson()

            val level = gson.fromJson(reader, Level::class.java)

            // recalculate positions of holes, obstacles, points
            for (hole in level.holes) {
                hole.positionInMeters.apply {
                    x = x * width.toFloat() / level.width.toFloat()
                    y = y * height.toFloat() / level.height.toFloat()
                }
            }
            for (obs in level.obstacles) {
                obs.apply {
                    x = (x.toFloat() * width.toFloat() / level.width.toFloat()).toInt()
                    y = (y.toFloat() * height.toFloat() / level.height.toFloat()).toInt()
                    x2 = (x2.toFloat() * width.toFloat() / level.width.toFloat()).toInt()
                    y2 = (y2.toFloat() * height.toFloat() / level.height.toFloat()).toInt()
                }
            }
            level.startingPosition.apply {
                x = x * width.toFloat() / level.width.toFloat()
                y = y * height.toFloat() / level.height.toFloat()
            }
            level.endPosition.apply {
                x = x * width.toFloat() / level.width.toFloat()
                y = y * height.toFloat() / level.height.toFloat()
            }
            level.width = width
            level.height = height

            return level
        }
    }
}