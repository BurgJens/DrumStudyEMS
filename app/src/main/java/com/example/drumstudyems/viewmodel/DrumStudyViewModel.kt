package com.example.drumstudyems.viewmodel

import androidx.lifecycle.ViewModel
import com.example.drumstudyems.model.RythmManager
import com.example.drumstudyems.model.Timer

class DrumStudyViewModel : ViewModel(){

    val timer = Timer(2000L)

    val rythmManager = RythmManager(timer)

    fun getCurrentTime() = timer.getCurrentTimeFlow()

    fun getActiveHits() = rythmManager.getActiveRythmFlow()

}