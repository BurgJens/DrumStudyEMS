package model

import com.example.drumstudyems.model.Rythm
import InputDelay
import RepeatRythm
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.atomic.AtomicBoolean

class RythmManager(timer: Timer, timeFrame : Long, onCompletion : () -> Unit) {

    var activeRythm = BaseRythm() as Rythm

    val activeDrumNotes = mutableListOf<DrumNote>()
    val oldDrumNotes = mutableListOf<DrumNote>()

    val activeDrumHits = mutableListOf<DrumHit>()
    val oldDrumHits = mutableListOf<DrumHit>()

    val metronome = mutableListOf<Long>()

    private val leftDrumInput = AtomicBoolean(false)
    private val rightDrumInput = AtomicBoolean(false)

    var lastInputLeft = -1L
    var lastInputRight = -1L

    var infiniteRuntime = AtomicBoolean(true)

    fun setLeftDrumInput(value : Boolean) {
        leftDrumInput.set(value)
    }
    fun setRightDrumInput(value : Boolean) {
        rightDrumInput.set(value)
    }

    fun setNewActiveRythm(newRythm : Rythm) {
        activeRythm = newRythm
    }


    private val activeRythmFlow = timer.getTimeDataFlow().onEach { timeData ->
        val rythmSegment = timeData.currentTime / activeRythm.tactDuration
        val timeInSegment = timeData.currentTime.rem(activeRythm.tactDuration)
        val timeOffset = timeFrame/3*2


        if (leftDrumInput.get() && (timeData.currentTime - lastInputLeft) > InputDelay) {
            lastInputLeft = timeData.currentTime
            activeDrumHits.add(DrumHit(lastInputLeft,LeftRight.LEFT))
            println("left")
        }
        leftDrumInput.set(false)
        if (rightDrumInput.get() && (timeData.currentTime - lastInputLeft) > InputDelay) {
            lastInputRight = timeData.currentTime
            activeDrumHits.add(DrumHit(lastInputRight, LeftRight.RIGHT))
            println("right")
        }
        rightDrumInput.set(false)

        if (rythmSegment <= RepeatRythm - 1){
            for (each in activeRythm.notes) {
                if (
                    each.playTime+timeOffset <= timeInSegment+timeOffset &&
                    each.playTime+timeOffset >= timeInSegment - timeData.deltaTime +timeOffset &&
                    each.timesAdded == rythmSegment.toInt()
                ) {
                    activeDrumNotes.add(
                        DrumNote(
                            segment = rythmSegment,
                            playTime = rythmSegment * activeRythm.tactDuration + each.playTime + timeOffset,
                            timeFrame = each.timeFrame,
                            side = each.side
                        )
                    )
                    each.timesAdded++
                }
            }
        }


        for (each in activeRythm.metronome){
            if (
                each + timeOffset <= timeInSegment + timeOffset &&
                each + timeOffset >= timeInSegment - timeData.deltaTime +timeOffset
            ) {
                metronome.add(rythmSegment * activeRythm.tactDuration + each + timeOffset)
            }
        }

        for(each in activeDrumNotes){
            if(each.playTime <= timeData.currentTime && !oldDrumNotes.contains(each)){
                oldDrumNotes.add(each)
            }
        }

        for(each in oldDrumNotes){
            if(each.playTime < timeData.currentTime - timeFrame/3){
                activeDrumNotes.remove(each)
            }
        }

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
        oldDrumNotes.clear()
        oldDrumHits.clear()
        leftDrumInput.set(false)
        rightDrumInput.set(false)
        lastInputLeft = -1L
        lastInputRight = -1L
        for (each in activeRythm.notes) each.timesAdded = 0
    }

    fun getActiveRythmFlow() = activeRythmFlow

}