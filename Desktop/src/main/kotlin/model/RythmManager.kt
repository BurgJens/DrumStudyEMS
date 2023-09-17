package model

import com.example.drumstudyems.model.BaseRythm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

data class RythmManagerData(val timeData: TimeData, val drumHits : List<DrumHit>, val input : List<Pair<LeftRight, Long>>)

class RythmManager(timer: Timer, timeFrame : Long) {

    private var acitveRythm = BaseRythm()

    val activeHits = mutableListOf<DrumHit>()
    val oldHits = mutableListOf<DrumHit>()

    val inputFlow = timer.getTimeDataFlow()

    val activeInput = mutableListOf<Pair<LeftRight, Long>>()
    val oldInput = mutableListOf<Pair<LeftRight, Long>>()


    private val leftDrumInput = MutableStateFlow(false)
    private val rightDrumInput = MutableStateFlow(false)

    var lastInputLeft = -1L
    var lastInputRight = -1L

    fun setLeftDrumInput(value : Boolean) {
        leftDrumInput.value = value
    }
    fun setRightDrumInput(value : Boolean) {
        rightDrumInput.value = value
    }

    private val activeRythmFlow = timer.getTimeDataFlow().onEach { timeData ->
        val rythmSegment = timeData.currentTime / acitveRythm.tactDuration
        val timeInSegment = timeData.currentTime.rem(acitveRythm.tactDuration)
        val timeOffset = timeFrame/3*2

        if (leftDrumInput.value && (timeData.currentTime - lastInputLeft) > 20L) {
            println("left")
            leftDrumInput.value = false
            lastInputLeft = timeData.currentTime
            activeInput.add(Pair(LeftRight.LEFT,lastInputLeft))
        }
        if (rightDrumInput.value && (timeData.currentTime - lastInputLeft) > 20L) {
            println("right")
            rightDrumInput.value = false
            lastInputRight = timeData.currentTime
            activeInput.add(Pair(LeftRight.RIGHT,lastInputRight))
        }

        for (each in acitveRythm.notes) {
            if (
                each.first+timeOffset <= timeInSegment+timeOffset &&
                each.first+timeOffset >= timeInSegment - timeData.deltaTime +timeOffset
            ) {
                activeHits.add(
                    DrumHit(
                        segment = rythmSegment,
                        hitTime = rythmSegment * acitveRythm.tactDuration + each.first + timeOffset,
                        side = each.second
                    )
                )
            }
        }
        val tempHits = mutableListOf<DrumHit>()
        for(each in activeHits){
            if(each.hitTime < timeData.currentTime - timeFrame/3){
                tempHits.add(each)
            }
        }
        for (each in tempHits){
            oldHits.add(each)
            activeHits.remove(each)
        }
        val tempInput = mutableListOf<Pair<LeftRight, Long>>()
        for(each in activeInput){
            if(each.second < timeData.currentTime - timeFrame/3){
                tempInput.add(each)
            }
        }
        for (each in tempInput){
            oldInput.add(each)
            activeInput.remove(each)
        }

    }.onCompletion {
        activeHits.clear()
    }

//    fun makeInput(currentTime : Long){
//        lastInputLeft = -1L
//        lastInputRight = -1L
//        input.add(currentTime)
//    }


    fun getActiveRythmFlow() = activeRythmFlow

}