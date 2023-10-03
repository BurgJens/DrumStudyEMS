package viewmodel

import MidiHandler
import AppTimeFrame
import com.example.drumstudyems.model.Rythm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import model.*
import moe.tlaster.precompose.viewmodel.ViewModel
import TimerPrecision
import java.util.concurrent.atomic.AtomicBoolean

enum class SupportMode(){
    NONE,
    EMS,
    AUDIO
}

class DrumStudyViewModel : ViewModel() {
    private val configManager = ConfigManager()
    private val timer = Timer(TimerPrecision)
    private val rythmManager = RythmManager(timer, AppTimeFrame, {writeLog()})
    private val emsHandler = EmsHandler(timer)
    private val midiHandler = MidiHandler()
    private val logWriter = LogWriter()


    val beeper = Beeper()

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
        if (supportMode.value == SupportMode.EMS){
//            var signalDT = it.timeData.currentTime - lastSignalSend
//            if (signalDT >= 50L){
//                for (each in it.drumNotes){
//
////            if ((each.playTime < it.timeData.currentTime + 500L) && !each.signaled){
////                emsHandler.sendCommandValues(each.side,100,500)
////                each.signaled = true
////            }
//
//                    if (each.playTime <= it.timeData.currentTime + 500L && each.playTime >= it.timeData.currentTime){
//
//
//
//                        emsHandler.sendCommandValues(each.side,100,50)
//                        lastSignalSend = it.timeData.currentTime
//                    }else if (each.playTime >= it.timeData.currentTime - 500L && each.playTime < it.timeData.currentTime){
//
//                    }
//                }
//            }

            var signalDT = it.timeData.currentTime - lastSignalSend
            if (signalDT >= 50L) {
                for (each in it.drumNotes) {

                    if ((each.playTime < it.timeData.currentTime + 250L) && !each.signaled){

                        emsHandler.sendCommandValues(each.side,100,250)

                        each.signaled = true
                    }
                }
            }

        }
        if (supportMode.value == SupportMode.AUDIO){
            var signalDT = it.timeData.currentTime - lastSignalSend
            if (signalDT >= 50L) {
                for (each in it.drumNotes) {

            if ((each.playTime < it.timeData.currentTime + 5L) && !each.signaled){

                when (each.side){
                    LeftRight.LEFT -> beeper.playSound(LeftRight.LEFT)
                    LeftRight.RIGHT -> beeper.playSound(LeftRight.RIGHT)
                }

                each.signaled = true
            }
                }
            }
        }

    }.onCompletion { lastSignalSend = 0L }

    var lastSignalSend = 0L

    fun sendEMScommand(cmd : String) = emsHandler.sendCommandString(cmd)
    fun getRythmManagerData() = rythmFlow

    fun debugButtonInpug(currentTime : Long){
//        rythmManager.makeInput(currentTime)
    }

    fun setLogMode(modeOn : Boolean){
        logAfter.set(modeOn)
        rythmManager.infiniteRuntime.set(false)
    }

    fun changeEmsIntensityMax(newValue : Int){
        emsIntensityMax.value = newValue
        saveCurrentConfig()
    }

    fun changeSubjectName(newName : String){
        subjectName.value = newName
        saveCurrentConfig()
    }

    val activeRythmFlow = MutableStateFlow(RythmsEnum.BASE_RYTHM)

    fun setRythm(rythmsEnum : RythmsEnum){
        var newRythm : Rythm

        when(rythmsEnum){
            RythmsEnum.BASE_RYTHM -> {
                newRythm = BaseRythm()
                activeRythmFlow.value = RythmsEnum.BASE_RYTHM
            }
            RythmsEnum.ER_RYTHM -> {
                newRythm = ErRythm()
                activeRythmFlow.value = RythmsEnum.ER_RYTHM
            }
            RythmsEnum.EL_RYTHM -> {
                newRythm = ElRythm()
                activeRythmFlow.value = RythmsEnum.EL_RYTHM
            }
            RythmsEnum.ML_RYTHM -> {
                newRythm = MlRythm()
                activeRythmFlow.value = RythmsEnum.ML_RYTHM
            }
            RythmsEnum.MR_RYTHM -> {
                newRythm = MrRythm()
                activeRythmFlow.value = RythmsEnum.MR_RYTHM
            }
            RythmsEnum.SL_RYTHM -> {
                newRythm = SlRythm()
                activeRythmFlow.value = RythmsEnum.SL_RYTHM
            }
            RythmsEnum.SR_RYTHM -> {
                newRythm = SrRythm()
                activeRythmFlow.value = RythmsEnum.SR_RYTHM
            }
        }
        rythmManager.setNewActiveRythm(newRythm)
    }

    val supportMode = MutableStateFlow(SupportMode.NONE)
    fun setSupportMode(supportModeEnum : SupportMode){
        var newMode : SupportMode

        when(supportModeEnum){
            SupportMode.NONE -> {
//                newRythm = BaseRythm()
                supportMode.value = SupportMode.NONE
            }
            SupportMode.AUDIO -> {
//                newRythm = ErRythm()
                supportMode.value = SupportMode.AUDIO
            }
            SupportMode.EMS -> {
//                newRythm = ElRythm()
                supportMode.value = SupportMode.EMS
            }
        }
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

    fun stopDrumListener(){
        midiHandler.stopListening()
    }

    fun resetConfigData() = configManager.resetConfigData()

    fun saveCurrentConfig(){
        configManager.writeNewConfigData(ConfigData(
            maxIntensity = emsIntensityMax.value,
            subjectName = subjectName.value
        ))
    }

}