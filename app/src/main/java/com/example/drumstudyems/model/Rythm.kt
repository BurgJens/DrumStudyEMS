package com.example.drumstudyems.model

abstract class Rythm {
    open val tactDuration : Long = 4 * 1000

    open val notes = listOf(
        Pair(1000L,LeftRight.LEFT),
        Pair(2000L,LeftRight.RIGHT),
        Pair(3000L,LeftRight.LEFT),
        Pair(4000L,LeftRight.RIGHT))
}