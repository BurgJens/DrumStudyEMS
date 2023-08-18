package com.example.drumstudyems.model

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


data class TimeData(val currentTime : Long, val deltaTime : Long)

class Timer(countDown : Long) {

    private val currentTimeFlow = flow{
        var startTime = System.currentTimeMillis()
        var deltaTime: Long
        var currentTime = 0L
        while (true){
            delay(10L)
            val last = currentTime
            val currentTimeGlobal = System.currentTimeMillis()
            currentTime = currentTimeGlobal - startTime
            deltaTime = currentTime - last



            emit(TimeData(currentTime,deltaTime))
        }
    }.flowOn(Dispatchers.Default)

    fun getTimeDataFlow() = currentTimeFlow
}