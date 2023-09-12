package model

import com.example.drumstudyems.model.BaseRythm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion

data class RythmManagerData(val timeData: TimeData, val drumHits : List<DrumHit>)

class RythmManager(timer: Timer, timeFrame : Long) {

    private var acitveRythm = BaseRythm()

    val activeHits = mutableListOf<DrumHit>()

    val oldHits = mutableListOf<DrumHit>()

    val inputFlow = timer.getTimeDataFlow()

    val input = mutableListOf<Long>()


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

    private val activeRythmFlow = timer.getTimeDataFlow().map { timeData ->

        val rythmSegment = timeData.currentTime / acitveRythm.tactDuration
        val timeInSegment = timeData.currentTime.rem(acitveRythm.tactDuration)
        val timeOffset = timeFrame/3*2


        if (leftDrumInput.value) {
            println("left")
            leftDrumInput.value = false
            println(lastInputLeft)
            lastInputLeft = timeData.currentTime
            println(lastInputLeft)
        }
        if (rightDrumInput.value) {
            println("right")
            rightDrumInput.value = false
            println(lastInputRight)
            lastInputRight = timeData.currentTime
            println(lastInputRight)
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
//        for(each in activeHits){
//            if(each.hitTime < timeData.currentTime){
//                oldHits.add(each)
//                activeHits.remove(each)
//            }
//        }
        RythmManagerData(timeData, activeHits.toList())
    }.onCompletion { println("ENDE") }

    fun makeInput(currentTime : Long){
        input.add(currentTime)
    }


    fun getActiveRythmFlow() = activeRythmFlow

}