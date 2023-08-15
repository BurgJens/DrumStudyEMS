package com.example.drumstudyems.viewmodel

import androidx.lifecycle.ViewModel
import com.example.drumstudyems.model.Timer

class DrumStudyViewModel : ViewModel(){

    val timer = Timer()

    fun getCurrentTime() = timer.getCurrentTime()

}