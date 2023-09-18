package model

import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.atomic.AtomicBoolean

class RythmManager(timer: Timer, timeFrame : Long, onCompletion : () -> Unit) {

    var acitveRythm = BaseRythm()

    val activeDrumNotes = mutableListOf<DrumNote>()
    val oldDrumNotes = mutableListOf<DrumNote>()

    val activeDrumHits = mutableListOf<DrumHit>()
    val oldDrumHits = mutableListOf<DrumHit>()

    val metronome = mutableListOf<Long>()

    private val leftDrumInput = AtomicBoolean(false)
    private val rightDrumInput = AtomicBoolean(false)

    var lastInputLeft = -1L
    var lastInputRight = -1L

    fun getActiveRythmName() = acitveRythm.name
    fun setLeftDrumInput(value : Boolean) {
        leftDrumInput.set(value)
    }
    fun setRightDrumInput(value : Boolean) {
        rightDrumInput.set(value)
    }

    private val activeRythmFlow = timer.getTimeDataFlow().onEach { timeData ->
        val rythmSegment = timeData.currentTime / acitveRythm.tactDuration
        val timeInSegment = timeData.currentTime.rem(acitveRythm.tactDuration)
        val timeOffset = timeFrame/3*2

        if (leftDrumInput.get() && (timeData.currentTime - lastInputLeft) > 100L) {
            println("left")
            leftDrumInput.set(false)
            lastInputLeft = timeData.currentTime
            activeDrumHits.add(DrumHit(lastInputLeft,LeftRight.LEFT))
        }
        if (rightDrumInput.get() && (timeData.currentTime - lastInputLeft) > 100L) {
            println("right")
            rightDrumInput.set(false)
            lastInputRight = timeData.currentTime
            activeDrumHits.add(DrumHit(lastInputRight, LeftRight.RIGHT))
        }

        for (each in acitveRythm.notes) {
            if (
                each.first+timeOffset <= timeInSegment+timeOffset &&
                each.first+timeOffset >= timeInSegment - timeData.deltaTime +timeOffset
            ) {
                activeDrumNotes.add(
                    DrumNote(
                        segment = rythmSegment,
                        playTime = rythmSegment * acitveRythm.tactDuration + each.first + timeOffset,
                        side = each.second
                    )
                )
            }
        }

        for (each in acitveRythm.metronome){
            if (
                each + timeOffset <= timeInSegment + timeOffset &&
                each + timeOffset >= timeInSegment - timeData.deltaTime +timeOffset
            ) {
                metronome.add(rythmSegment * acitveRythm.tactDuration + each + timeOffset)
            }
        }

        val tempNotes = mutableListOf<DrumNote>()
        for(each in activeDrumNotes){
            if(each.playTime < timeData.currentTime - timeFrame/3){
                tempNotes.add(each)
            }
        }
        activeDrumNotes.removeAll(tempNotes)
        oldDrumNotes.addAll(tempNotes)

        val tempHits = mutableListOf<DrumHit>()
        for(each in activeDrumHits){
            if(each.hitTime < timeData.currentTime - timeFrame/3){
                tempHits.add(each)
            }
        }
        activeDrumHits.removeAll(tempHits)
        oldDrumHits.addAll(tempHits)

        metronome.removeIf { it < timeData.currentTime - timeFrame/3}

    }.onCompletion {
        activeDrumNotes.sortBy {it.playTime}
        activeDrumHits.sortBy {it.hitTime}
        onCompletion()
        activeDrumNotes.clear()
        activeDrumHits.clear()
    }

    fun getActiveRythmFlow() = activeRythmFlow

}