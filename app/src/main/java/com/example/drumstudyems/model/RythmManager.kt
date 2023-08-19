package com.example.drumstudyems.model

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

data class RythmManagerData(val timeData: TimeData, val drumHits : List<DrumHit>)

class RythmManager(timer: Timer) {

    private var acitveRythm = BaseRythm()

    val activeHits = mutableListOf<DrumHit>()

    val oldHits = mutableListOf<DrumHit>()

    val inputFlow = timer.getTimeDataFlow()




    private val activeRythmFlow = timer.getTimeDataFlow().map { time ->

        val rythmSegment = time.currentTime.mod(acitveRythm.tactDuration)
        val timeInSegment = time.currentTime.rem(acitveRythm.tactDuration)

        for (hitTime in acitveRythm.leftDrum) {
            if (
                hitTime <= timeInSegment &&
                hitTime >= timeInSegment - time.deltaTime
            ) {
//                Log.d("RythmManager", "hit geaddet")
                activeHits.add(
                    DrumHit(
                        segment = rythmSegment,
                        hitTime = rythmSegment * acitveRythm.tactDuration + hitTime,
                        side = LeftRight.LEFT
                    )
                )
                Log.d("RythmManager", "${activeHits}")
            }
        }
        for (hitTime in acitveRythm.rightDrum) {
            if (
                hitTime <= timeInSegment &&
                hitTime >= timeInSegment - time.deltaTime
            ) {
//                Log.d("RythmManager", "hit geaddet")
                activeHits.add(
                    DrumHit(
                        segment = rythmSegment,
                        hitTime = rythmSegment * acitveRythm.tactDuration + hitTime,
                        side = LeftRight.Right
                    )
                )
                Log.d("RythmManager", "${activeHits}")
            }
        }
        RythmManagerData(time, activeHits.toList())
    }


    fun getActiveRythmFlow() = activeRythmFlow

}