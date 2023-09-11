import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import view.AppNavigation
import viewmodel.DrumStudyViewModel

val windowSize = DpSize(1920.dp,1080.dp)

@Composable
@Preview
fun App() {
    val drumStudyViewModel = DrumStudyViewModel()

    MaterialTheme {
        AppNavigation(drumStudyViewModel)
    }
}


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(size = windowSize)
    ) {
        App()
    }
}
