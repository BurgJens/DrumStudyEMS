package com.example.drumstudyems.model

interface Rythm {
    open val tactDuration : Long

    open val notes : List<Pair<Long,LeftRight>>
}