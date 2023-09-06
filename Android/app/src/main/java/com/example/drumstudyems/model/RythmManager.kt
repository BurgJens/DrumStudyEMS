package com.example.drumstudyems.model

import kotlinx.coroutines.flow.map

data class RythmManagerData(val timeData: TimeData, val drumHits : List<DrumHit>)

class RythmManager(timer: Timer, timeFrame : Long) {

    private var acitveRythm = BaseRythm()

    val activeHits = mutableListOf<DrumHit>()

    val oldHits = mutableListOf<DrumHit>()

    val inputFlow = timer.getTimeDataFlow()

    val input = mutableListOf<Long>()




    private val activeRythmFlow = timer.getTimeDataFlow().map { timeData ->

        val rythmSegment = timeData.currentTime / acitveRythm.tactDuration
        val timeInSegment = timeData.currentTime.rem(acitveRythm.tactDuration)
        val timeOffset = timeFrame/3*2

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
    }

    fun makeInput(currentTime : Long){
        input.add(currentTime)
    }


    fun getActiveRythmFlow() = activeRythmFlow

}