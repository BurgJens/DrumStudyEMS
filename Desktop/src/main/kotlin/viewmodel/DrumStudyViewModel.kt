package viewmodel

import MidiHandler
import model.RythmManager
import model.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel

class DrumStudyViewModel : ViewModel() {

    private val timer = Timer(2000L, 16)
    private val rythmManager = RythmManager(timer, 5000L)
    private val midiHandler = MidiHandler()

    val rythmFlow = rythmManager.getActiveRythmFlow()
    fun getRythmManagerData() = rythmFlow

    fun startTimer() = timer.getTimeDataFlow()

    fun debugButtonInpug(currentTime : Long){
        rythmManager.makeInput(currentTime)
    }

    fun startDrumListener(){
        CoroutineScope(Dispatchers.Default).launch{
            midiHandler.startListening { note ->
                if (note == 48) rythmManager.setLeftDrumInput(true)
                if (note == 45) rythmManager.setRightDrumInput(true)
            }
        }
    }

}