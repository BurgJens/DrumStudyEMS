package com.example.drumstudyems.model

abstract class Rythm {
    open val tactDuration : Long = 4 * 1000

    open val leftDrum = listOf(1000L, 3000L)
    open val rightDrum = listOf(2000L, 4000L)
}