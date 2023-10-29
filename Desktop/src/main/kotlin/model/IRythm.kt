package com.example.drumstudyems.model

import model.RythmNote

interface IRythm {
    open val repeat : Int

    open val name : String

    open val duration : Long

    open val notes : List<RythmNote>

    open val metronome : List<Long>
}