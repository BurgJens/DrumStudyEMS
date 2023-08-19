package com.example.drumstudyems.model

class BaseRythm() : Rythm() {
    override val tactDuration : Long = 4 * 1000

    override val notes = listOf(
        Pair(500L,LeftRight.LEFT),
        Pair(1500L,LeftRight.RIGHT),
        Pair(2500L,LeftRight.LEFT),
        Pair(3500L,LeftRight.RIGHT))

}