package com.example.drumstudyems.viewmodel

import androidx.lifecycle.ViewModel
import com.example.drumstudyems.model.RythmManager
import com.example.drumstudyems.model.Timer

class DrumStudyViewModel : ViewModel(){

    val timer = Timer(2000L, 16)

    val rythmManager = RythmManager(timer, 5000L)

    fun getRythmManagerData() = rythmManager.getActiveRythmFlow()

    fun startTimer() = timer.getTimeDataFlow()

    fun debugButtonInpug(currentTime : Long){
        rythmManager.makeInput(currentTime)
    }
}