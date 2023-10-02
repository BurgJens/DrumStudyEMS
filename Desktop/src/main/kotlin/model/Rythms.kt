package model

import TimeFrameToHit
import com.example.drumstudyems.model.Rythm

enum class RythmsEnum (){
    BASE_RYTHM,
    ER_RYTHM,
    EL_RYTHM,
    MR_RYTHM,
    ML_RYTHM
}

class BaseRythm() : Rythm {
    override val name = "BaseRythm"

    override val tactDuration : Long = 4 * 1000

    override val notes = listOf(
        RythmNote(0L, 124L, LeftRight.LEFT),
        RythmNote(1000L, 124L, LeftRight.RIGHT),
        RythmNote(2000L, 124L, LeftRight.LEFT),
        RythmNote(3000L, 124L, LeftRight.RIGHT))

    override val metronome = listOf(0L)
}

class ErRythm() : Rythm {
    override val name = "ER"

    val speedMult = 1.25

    override val tactDuration : Long = (4000L * speedMult).toLong()

    override val notes = listOf(
        RythmNote((0 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((500 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),

        RythmNote((1000 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((1500 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),

        RythmNote((2000 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((2250 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((2500 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((2750 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),

        RythmNote((3000 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((3250 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((3500 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
    )

    override val metronome = listOf(0L)
}

class ElRythm() : Rythm {
    override val name = "EL"

    val speedMult = 1.25

    override val tactDuration : Long = (4000L * speedMult).toLong()

    override val notes = listOf(
        RythmNote((0 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((500 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),

        RythmNote((1000 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((1500 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),

        RythmNote((2000 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((2250 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((2500 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((2750 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),

        RythmNote((3000 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((3250 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((3500 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
    )

    override val metronome = listOf(0L)
}

class MlRythm() : Rythm {
    override val name = "ML"

    val speedMult = 1.25

    override val tactDuration : Long = (4000L * speedMult).toLong()

    override val notes = listOf(
        RythmNote((0 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((250 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((500 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((750 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),

        RythmNote((1000 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((1250 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((1500 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((1750 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),

        RythmNote((2000 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((2250 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((2500 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((2750 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),

        RythmNote((3000 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((3250 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((3500 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((3750 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
    )

    override val metronome = listOf(0L)
}

class MrRythm() : Rythm {
    override val name = "ML"

    val speedMult = 1.25

    override val tactDuration : Long = (4000L * speedMult).toLong()

    override val notes = listOf(
        RythmNote((0 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((250 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((500 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((750 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),

        RythmNote((1000 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((1250 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((1500 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((1750 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),

        RythmNote((2000 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((2250 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((2500 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((2750 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),

        RythmNote((3000 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((3250 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((3500 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((3750 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
    )
    override val metronome = listOf(0L)
}