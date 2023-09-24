package model

import com.example.drumstudyems.model.Rythm

enum class RythmsEnum (){
    BASE_RYTHM,
    ER_RYTHM,
    EL_RYTHM
}

class BaseRythm() : Rythm {
    override val name = "BaseRythm"

    override val tactDuration : Long = 4 * 1000

    override val notes = listOf(
        RythmNote(500L, 124L, LeftRight.LEFT),
        RythmNote(1500L, 124L, LeftRight.RIGHT),
        RythmNote(2500L, 124L, LeftRight.LEFT),
        RythmNote(3500L, 124L, LeftRight.RIGHT))

    override val metronome = listOf(0L)
}

class ErRythm() : Rythm {
    override val name = "ER"

    val speedMult = 1.25

    override val tactDuration : Long = (4000L * speedMult).toLong()

    override val notes = listOf(
        RythmNote((0 * speedMult).toLong(), 124L,  LeftRight.RIGHT),
        RythmNote((500 * speedMult).toLong(), 124L, LeftRight.LEFT),

        RythmNote((1000 * speedMult).toLong(), 124L, LeftRight.RIGHT),
        RythmNote((1500 * speedMult).toLong(), 124L, LeftRight.LEFT),

        RythmNote((2000 * speedMult).toLong(), 124L, LeftRight.RIGHT),
        RythmNote((2250 * speedMult).toLong(), 124L, LeftRight.RIGHT),
        RythmNote((2500 * speedMult).toLong(), 124L, LeftRight.LEFT),
        RythmNote((2750 * speedMult).toLong(), 124L, LeftRight.LEFT),

        RythmNote((3000 * speedMult).toLong(), 124L, LeftRight.RIGHT),
        RythmNote((3250 * speedMult).toLong(), 124L, LeftRight.RIGHT),
        RythmNote((3500 * speedMult).toLong(), 124L, LeftRight.LEFT),
    )

    override val metronome = listOf(0L)
}

class ElRythm() : Rythm {
    override val name = "EL"

    val speedMult = 1.25

    override val tactDuration : Long = (4000L * speedMult).toLong()

    override val notes = listOf(
        RythmNote((0 * speedMult).toLong(), 124L,  LeftRight.LEFT),
        RythmNote((500 * speedMult).toLong(), 124L, LeftRight.RIGHT),

        RythmNote((1000 * speedMult).toLong(), 124L, LeftRight.LEFT),
        RythmNote((1500 * speedMult).toLong(), 124L, LeftRight.RIGHT),

        RythmNote((2000 * speedMult).toLong(), 124L, LeftRight.LEFT),
        RythmNote((2250 * speedMult).toLong(), 124L, LeftRight.LEFT),
        RythmNote((2500 * speedMult).toLong(), 124L, LeftRight.RIGHT),
        RythmNote((2750 * speedMult).toLong(), 124L, LeftRight.RIGHT),

        RythmNote((3000 * speedMult).toLong(), 124L, LeftRight.LEFT),
        RythmNote((3250 * speedMult).toLong(), 124L, LeftRight.LEFT),
        RythmNote((3500 * speedMult).toLong(), 124L, LeftRight.RIGHT),
    )

    override val metronome = listOf(0L)
}