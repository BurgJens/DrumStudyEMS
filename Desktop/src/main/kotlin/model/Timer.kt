package model

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion


data class TimeData(val currentTime : Long, val deltaTime : Long)

class Timer(val delay : Long) {

    private val currentTimeFlow = flow{
        var startTime = System.currentTimeMillis()
        var deltaTime: Long
        var currentTime = 0L
        while (true){
            delay(delay)
            val last = currentTime
            val currentTimeGlobal = System.currentTimeMillis()
            currentTime = currentTimeGlobal - startTime
            deltaTime = currentTime - last


            println(deltaTime)
            emit(TimeData(currentTime,deltaTime))
        }
    }.flowOn(Dispatchers.Default)

    fun getTimeDataFlow() = currentTimeFlow
}