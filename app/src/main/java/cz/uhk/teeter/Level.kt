package cz.uhk.teeter

class Level {

    var width = 0
    var height = 0

    lateinit var startingPosition: Point2D
    lateinit var endPosition: Point2D

    var obstacles = mutableListOf<Obstacle>()
    var holes = mutableListOf<Hole>()

}