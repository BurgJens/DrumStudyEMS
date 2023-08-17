package com.example.drumstudyems.model


import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore


data class Time(val currentTime : Long, val deltaTime : Long)

    val mutex = Semaphore(1)
    var curTimeDelT = Pair(0L,0L)

class Timer(countDown : Long) {

    private val currentTimeFlow : Flow<Pair<Long, Long>> = flow{
        var startTime = System.currentTimeMillis()
        var deltaTime: Long
        var currentTime = 0L
        while (true){
            delay(10L)
            val last = currentTime
            val now = System.currentTimeMillis()
            currentTime = now - startTime
            deltaTime = currentTime - last

            mutex.acquire()
            curTimeDelT = Pair(currentTime,deltaTime)
            mutex.release()

            emit(Pair(currentTime,deltaTime))
        }
    }.flowOn(Dispatchers.Default)

    fun getCurrentTimeFlow() = currentTimeFlow

    suspend fun getCurrentTime() : Pair<Long, Long>{
        var temp = Pair(0L,0L)
        mutex.acquire()
        temp = curTimeDelT
        mutex.release()
        return temp
    }
}