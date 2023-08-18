package com.example.drumstudyems.model

enum class LeftRight{
    LEFT,
    Right
}
data class DrumHit (var segment : Long, var hitTime : Long, var side : LeftRight)
