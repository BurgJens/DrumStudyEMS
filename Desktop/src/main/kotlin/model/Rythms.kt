package model

import TimeFrameToHit
import com.example.drumstudyems.model.Rythm

enum class RythmsEnum (){
    BASE_RYTHM,
    ER_RYTHM,
    EL_RYTHM,
    MR_RYTHM,
    ML_RYTHM,
    SR_RYTHM,
    SL_RYTHM
}


class BaseRythm() : Rythm {
    override val repeat = 999

    override val name = "BaseRythm"

    override val tactDuration : Long = 4 * 1000

    override val notes = listOf(
        RythmNote(0L, 124L, LeftRight.LEFT),
        RythmNote(1000L, 124L, LeftRight.RIGHT),
        RythmNote(2000L, 124L, LeftRight.LEFT),
        RythmNote(3000L, 124L, LeftRight.RIGHT))

    override val metronome = listOf(0L)
}

open class ElRythm() : Rythm {
    override val repeat = 4

    override val name = "EL"

    val speedMult = 1.5

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

class ErRythm() : ElRythm() {
    override val name = "ER"

    init {
        for (each in notes){
            when(each.side){
                LeftRight.LEFT -> each.side = LeftRight.RIGHT
                LeftRight.RIGHT -> each.side = LeftRight.LEFT
            }
        }
    }
}



open class MlRythm() : Rythm {
    override val repeat = 4

    override val name = "ML"

    val speedMult = 1.5

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

class MrRythm() : MlRythm() {
    override val name = "MR"
    init {
        for (each in notes){
            when(each.side){
                LeftRight.LEFT -> each.side = LeftRight.RIGHT
                LeftRight.RIGHT -> each.side = LeftRight.LEFT
            }
        }
    }
}

open class SlRythm() : Rythm {
    override val repeat = 2

    override val name = "SL"

    val speedMult = 1.8

    override val tactDuration : Long = (8000L * speedMult).toLong()

    override val notes = listOf(
        RythmNote((0 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((200 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((400 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((750 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),

        RythmNote((1000 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((1200 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((1400 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((1750 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),

        RythmNote((2000 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((2200 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((2400 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((2750 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),

        RythmNote((3000 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((3200 * speedMult).toLong(), TimeFrameToHit,  LeftRight.LEFT),
        RythmNote((3400 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
        RythmNote((3750 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),


        RythmNote((4000 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((4200 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((4400 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((4750 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),

        RythmNote((5000 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((5200 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((5400 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((5750 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),

        RythmNote((6000 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((6200 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((6400 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((6750 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),

        RythmNote((7000 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((7200 * speedMult).toLong(), TimeFrameToHit,  LeftRight.RIGHT),
        RythmNote((7400 * speedMult).toLong(), TimeFrameToHit, LeftRight.RIGHT),
        RythmNote((7750 * speedMult).toLong(), TimeFrameToHit, LeftRight.LEFT),
    )
    override val metronome = listOf(0L, (4000L * speedMult).toLong(), )
}


class SrRythm() : SlRythm() {
    override val name = "SR"
    init {
        for (each in notes){
            when(each.side){
                LeftRight.LEFT -> each.side = LeftRight.RIGHT
                LeftRight.RIGHT -> each.side = LeftRight.LEFT
            }
        }
    }
}