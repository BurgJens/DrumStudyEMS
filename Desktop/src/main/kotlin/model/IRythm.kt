package com.example.drumstudyems.model

import model.LeftRight
import model.RythmNote

interface Rythm {
    open val repeat : Int

    open val name : String

    open val tactDuration : Long

    open val notes : List<RythmNote>

    open val metronome : List<Long>
}