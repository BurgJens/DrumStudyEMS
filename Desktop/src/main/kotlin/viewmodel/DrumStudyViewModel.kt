package viewmodel

import MidiHandler
import appTimeFrame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import model.*
import moe.tlaster.precompose.viewmodel.ViewModel
import timerPrecision
import java.util.concurrent.atomic.AtomicBoolean

class DrumStudyViewModel : ViewModel() {


    private val configManager = ConfigManager()
    private val timer = Timer(timerPrecision)
    private val rythmManager = RythmManager(timer, appTimeFrame, {writeLog()})
    private val emsHandler = EmsHandler(timer)
    private val midiHandler = MidiHandler()
    private val logWriter = LogWriter()

    val subjectName = MutableStateFlow(configManager.configData.subjectName)

    val emsIntensityMax = MutableStateFlow(configManager.configData.maxIntensity)

    val logAfter = AtomicBoolean(false)

    val rythmFlow = rythmManager.getActiveRythmFlow().map {timeData ->
        RythmManagerData(
            timeData,
            rythmManager.activeDrumNotes.toList(),
            rythmManager.activeDrumHits.toList(),
            rythmManager.metronome.toList()
        )
    }.onEach {
        for (each in it.drumNotes){
            if (each.playTime <= it.timeData.currentTime + 500L && each.playTime >= it.timeData.currentTime){

                emsHandler.sendCommandValues(each.side,100,2)
//                emsHandler.sendCommandString("C1I100T1000G")
            }else if (each.playTime >= it.timeData.currentTime - 500L && each.playTime < it.timeData.currentTime){

            }
        }
    }

    fun sendEMScommand(cmd : String) = emsHandler.sendCommandString(cmd)
    fun getRythmManagerData() = rythmFlow

    fun debugButtonInpug(currentTime : Long){
//        rythmManager.makeInput(currentTime)
    }

    fun changeEmsIntensityMax(newValue : Int){
        emsIntensityMax.value = newValue
        saveCurrentConfig()
    }

    fun changeSubjectName(newName : String){
        subjectName.value = newName
        saveCurrentConfig()
    }

    fun setRythm(rythmsEnum : RythmsEnum){
        val newRythm = when(rythmsEnum){
            RythmsEnum.BASE_RYTHM -> BaseRythm()
            RythmsEnum.ER_RYTHM -> ErRythm()
            RythmsEnum.EL_RYTHM -> ElRythm()
        }
        rythmManager.setNewActiveRythm(newRythm)
    }

    fun writeLog(){
        if (logAfter.get()) logWriter.logData(subjectName.value,rythmManager.activeRythm,rythmManager.oldDrumNotes,rythmManager.oldDrumHits)
    }

    fun startDrumListener(){
        CoroutineScope(Dispatchers.Default).launch{
            midiHandler.startListening { note ->
                if (note == 48) rythmManager.setLeftDrumInput(true)
                if (note == 45) rythmManager.setRightDrumInput(true)
            }
        }
    }

    fun resetConfigData() = configManager.resetConfigData()

    fun saveCurrentConfig(){
        configManager.writeNewConfigData(ConfigData(
            maxIntensity = emsIntensityMax.value,
            subjectName = subjectName.value
        ))
    }

}