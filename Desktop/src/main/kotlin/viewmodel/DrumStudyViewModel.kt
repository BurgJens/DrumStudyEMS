package viewmodel

import MidiHandler
import appTimeFrame
import model.RythmManager
import model.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import model.LogWriter
import model.RythmManagerData
import moe.tlaster.precompose.viewmodel.ViewModel
import java.util.concurrent.atomic.AtomicBoolean

class DrumStudyViewModel : ViewModel() {

    private val timer = Timer(2000L, 16)
    private val rythmManager = RythmManager(timer, appTimeFrame, {writeLog()})
    private val midiHandler = MidiHandler()
    private val logWriter = LogWriter()

    val subjectName = MutableStateFlow("Subject01")

    val logAfter = AtomicBoolean(false)

    val rythmFlow = rythmManager.getActiveRythmFlow().map {timeData ->
        RythmManagerData(
            timeData,
            rythmManager.activeDrumNotes.toList(),
            rythmManager.activeDrumHits.toList(),
            rythmManager.metronome.toList()
        )
    }
    fun getRythmManagerData() = rythmFlow

    fun debugButtonInpug(currentTime : Long){
//        rythmManager.makeInput(currentTime)
    }

    fun changeSubjectName(newName : String){
        subjectName.value = newName
    }

    fun writeLog(){
        if (logAfter.get()) logWriter.logData(subjectName.value,rythmManager.getActiveRythmName(),rythmManager.oldDrumNotes,rythmManager.oldDrumHits)
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