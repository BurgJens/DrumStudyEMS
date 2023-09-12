package view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import viewmodel.DrumStudyViewModel

sealed class NavScreen(val route : String){
    object Start : NavScreen("start")
    object Drum : NavScreen("drum")
}

@Composable
fun AppNavigation(drumStudyViewModel: DrumStudyViewModel){

    val screen = remember{ mutableStateOf(NavScreen.Start.route) }
    fun changeScreen (route : String){
        screen.value = route
    }

    when(screen.value){
        NavScreen.Start.route ->
            ScreenStartHandler {
                drumStudyViewModel.startDrumListener()
                changeScreen(NavScreen.Drum.route)
            }

        NavScreen.Drum.route ->
            ScreenDrumHeroData(
                drumStudyViewModel,
                {changeScreen(NavScreen.Start.route)}
        )
    }

}