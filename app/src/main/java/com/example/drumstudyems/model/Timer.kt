package com.example.drumstudyems.model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Job
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong


class Timer {

    var timer : Job? = null

    var startTime = AtomicLong(0)
    var isRunning = AtomicBoolean(false)
    var shouldRun = AtomicBoolean(false)


    private val currentTime = MutableLiveData(0L)

    fun start() {
        if (timer == null) {
            shouldRun.set(true)
            isRunning.set(true)
            timer = CoroutineScope(Dispatchers.Default).launch{
                startTime.set(System.currentTimeMillis())
                while (shouldRun.get()){
                    currentTime.setValue(System.currentTimeMillis() - startTime.get())
                }
                isRunning.set(false)
            }
        }
    }

    fun stop(){
        if(isRunning.get()){
            shouldRun.set(false)
        }
    }

    fun getCurrentTime() = currentTime
}