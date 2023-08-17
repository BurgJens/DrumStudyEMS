package com.example.drumstudyems.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RythmManager(timer: Timer) {

    private var acitveRythm = BaseRythm()

    val activeLeftHit = mutableListOf<DrumHit>()
    val activeRightHit = mutableListOf<DrumHit>()

    val oldLeftHit = mutableListOf<DrumHit>()
    val oldRightHit = mutableListOf<DrumHit>()

    private val activeRythmFlow = flow<Pair<List<DrumHit>,List<DrumHit>>> {
        while (true){
            val value = timer.getCurrentTime()
            val currentTime = value.first
            val deltaTime = value.second

            val rythmSegment = currentTime.mod(acitveRythm.tactDuration)
            val timeInSegment = currentTime.rem(acitveRythm.tactDuration)

            for (hitTime in acitveRythm.leftDrum){
                if(
                    hitTime <= timeInSegment &&
                    hitTime >= timeInSegment - deltaTime
                ){
                    activeLeftHit.add(DrumHit(
                        segment = rythmSegment,
                        hitTime = rythmSegment*acitveRythm.tactDuration + hitTime)
                    )
                }
            }

            emit(Pair(activeLeftHit,activeRightHit))

        }
    }.flowOn(Dispatchers.Default)

    fun getActiveRythmFlow() = activeRythmFlow


}