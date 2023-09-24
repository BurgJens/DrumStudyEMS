package view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import viewmodel.DrumStudyViewModel

sealed class NavScreen(val route : String){
    object Start : NavScreen("start")
    object Drum : NavScreen("drum")
    object EMS : NavScreen("ems")
}

@Composable
fun AppNavigation(drumStudyViewModel: DrumStudyViewModel){

    val screen = remember{ mutableStateOf(NavScreen.Start.route) }
    fun changeScreen (route : String){
        screen.value = route
    }

    when(screen.value){
        NavScreen.Start.route ->
            ScreenStartHandler (
                drumStudyViewModel = drumStudyViewModel,
                navigateDrumHero = {
                    drumStudyViewModel.startDrumListener()
                    changeScreen(NavScreen.Drum.route)
                },
                navigateEMSsetup = {changeScreen(NavScreen.EMS.route)}
            )

        NavScreen.Drum.route ->
            ScreenDrumHeroData(
                drumStudyViewModel= drumStudyViewModel,
                navigateBack = {changeScreen(NavScreen.Start.route)}
        )

        NavScreen.EMS.route ->
            ScreenEMSsetup(
                navigateBack = {changeScreen(NavScreen.Start.route)},
                drumStudyViewModel = drumStudyViewModel
            )
    }

}