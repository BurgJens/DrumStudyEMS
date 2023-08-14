package com.example.drumstudyems.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drumstudyems.ui.theme.DrumStudyEMSTheme
import com.example.drumstudyems.view.elements.fallingPoint

@Composable
fun ScreenDrumHero (){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val leftPosX = screenWidth/3
    val rightPosX = screenWidth/3*2

    Box(
        modifier = Modifier.fillMaxSize()
    )
    {
        Row {
            RythmBox()
            RythmBox()
        }
    }

}

@Composable
fun RythmBox(){
    Box (

    )
    {

    }
}

@Preview(showBackground = true)
@Composable
fun ScreenDrumHeroPreview() {
    DrumStudyEMSTheme {
        ScreenDrumHero()
    }
}