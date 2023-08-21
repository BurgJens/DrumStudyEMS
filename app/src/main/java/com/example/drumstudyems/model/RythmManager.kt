package com.example.drumstudyems.model

import android.util.Log
import kotlinx.coroutines.flow.map

data class RythmManagerData(val timeData: TimeData, val drumHits : List<DrumHit>)

class RythmManager(timer: Timer, timeFrame : Long) {

    private var acitveRythm = BaseRythm()

    val activeHits = mutableListOf<DrumHit>()

    val oldHits = mutableListOf<DrumHit>()

    val inputFlow = timer.getTimeDataFlow()




    private val activeRythmFlow = timer.getTimeDataFlow().map { timeData ->

        val rythmSegment = timeData.currentTime / acitveRythm.tactDuration
        Log.d("activeRythmFlow", "rythmSegment" + rythmSegment)
        val timeInSegment = timeData.currentTime.rem(acitveRythm.tactDuration)
        Log.d("activeRythmFlow", "timeInSegment" + timeInSegment)

        val test = timeFrame/3*2

        for (each in acitveRythm.notes) {
            if (
                each.first+test <= timeInSegment+test &&
                each.first+test >= timeInSegment - timeData.deltaTime +test
            ) {
//                Log.d("RythmManager", "hit geaddet")
                activeHits.add(
                    DrumHit(
                        segment = rythmSegment,
                        hitTime = rythmSegment * acitveRythm.tactDuration + each.first + test,
                        side = each.second
                    )
                )
//                Log.d("RythmManager", "${activeHits}")
            }
        }
//        for(each in activeHits){
//            if(each.hitTime < timeData.currentTime + each.hitTime){
//                oldHits.add(each)
//                activeHits.remove(each)
//            }
//        }

        RythmManagerData(timeData, activeHits.toList())
    }


    fun getActiveRythmFlow() = activeRythmFlow

}