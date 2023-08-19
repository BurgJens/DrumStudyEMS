package com.example.drumstudyems.model

import android.util.Log
import kotlinx.coroutines.flow.map

data class RythmManagerData(val timeData: TimeData, val drumHits : List<DrumHit>)

class RythmManager(timer: Timer, timeFrame : Long) {

    private var acitveRythm = BaseRythm()

    val activeHits = mutableListOf<DrumHit>()

    val oldHits = mutableListOf<DrumHit>()

    val inputFlow = timer.getTimeDataFlow()




    private val activeRythmFlow = timer.getTimeDataFlow().map { time ->

        val rythmSegment = time.currentTime / acitveRythm.tactDuration
        Log.d("activeRythmFlow", "rythmSegment" + rythmSegment)
        val timeInSegment = time.currentTime.rem(acitveRythm.tactDuration)
        Log.d("activeRythmFlow", "timeInSegment" + timeInSegment)

        for (each in acitveRythm.notes) {
            if (
                each.first <= timeInSegment &&
                each.first >= timeInSegment - time.deltaTime
            ) {
//                Log.d("RythmManager", "hit geaddet")
                activeHits.add(
                    DrumHit(
                        segment = rythmSegment,
                        hitTime = rythmSegment * acitveRythm.tactDuration + each.first,
                        side = each.second
                    )
                )
//                Log.d("RythmManager", "${activeHits}")
            }
        }
        for(each in activeHits){
            if(each.hitTime > time.currentTime + each.hitTime){
                oldHits.add(each)
                activeHits.remove(each)
            }
        }

        RythmManagerData(time, activeHits.toList())
    }


    fun getActiveRythmFlow() = activeRythmFlow

}