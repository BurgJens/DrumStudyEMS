import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import view.AppNavigation
import viewmodel.DrumStudyViewModel
import javax.sound.midi.*

val appWindowSize = DpSize(1920.dp,1080.dp)
val appTimeFrame = 7500L
val appDebugMode = false

@Composable
@Preview
fun App() {
    val drumStudyViewModel = DrumStudyViewModel()

    MaterialTheme {
        AppNavigation(drumStudyViewModel)
    }
}


fun main() = application {

    val midiDeviceInfo = MidiSystem.getMidiDeviceInfo()

    for (each in midiDeviceInfo){
        println("${each.name}    ${each.description}    ${each.version}    ${each.vendor}")
    }


    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(size = appWindowSize, position = WindowPosition(Alignment.TopCenter))
    ) {
        App()
    }
}
