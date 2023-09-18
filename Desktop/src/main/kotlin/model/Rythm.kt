package com.example.drumstudyems.model

import model.LeftRight

interface Rythm {
    open val name : String

    open val tactDuration : Long

    open val notes : List<Pair<Long, LeftRight>>

    open val metronome : List<Long>
}