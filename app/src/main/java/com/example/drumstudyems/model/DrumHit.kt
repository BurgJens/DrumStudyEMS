package com.example.drumstudyems.model

enum class LeftRight{
    LEFT,
    RIGHT
}
data class DrumHit (var segment : Long, var hitTime : Long, var side : LeftRight)
