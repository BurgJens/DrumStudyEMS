package com.example.drumstudyems.model

class BaseRythm() : Rythm() {

    override val tactDuration : Long = 4 * 1000

    override val leftDrum = listOf(1000L, 3000L)
    override val rightDrum = listOf(2000L, 4000L)

}