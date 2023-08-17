package com.example.drumstudyems.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.drumstudyems.model.DrumHit
import com.example.drumstudyems.ui.theme.DrumStudyEMSTheme
import com.example.drumstudyems.view.elements.drumPoint
import com.example.drumstudyems.viewmodel.DrumStudyViewModel
import kotlinx.coroutines.Dispatchers



@Composable
fun ScreenDrumHeroData(drumStudyViewModel: DrumStudyViewModel){

    val currentTimeFlow by
    drumStudyViewModel.getCurrentTime()
        .collectAsState(initial = Pair(0L,0L), Dispatchers.Main)

    val activeHitsFlow by
    drumStudyViewModel.getActiveHits()
        .collectAsState(initial = Pair(listOf<DrumHit>(),listOf<DrumHit>()), Dispatchers.Main)

//    val activeHitsFlow = Pair(listOf<DrumHit>(),listOf<DrumHit>())

    ScreenDrumHero(
        currentTimeFlow,
        activeHitsFlow
    )
}

@Composable
fun ScreenDrumHero(
    currentTime: Pair<Long ,Long>,
    drumhits: Pair<List<DrumHit>, List<DrumHit>>
){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    )
    {
        Row {
            RythmBox(Color.Gray, screenWidth/2, screenHeight, drumhits.first)
            RythmBox(Color.LightGray, screenWidth/2, screenHeight, drumhits.second)
        }
        Divider(
            modifier = Modifier
                .height(10.dp)
                .offset(y = screenHeight / 3 * 2),
            color = Color.Cyan,
        )

        showTimer(currentTime, screenHeight/100*95)
    }

}

@Composable
fun RythmBox(color : Color, width : Dp, height : Dp, drumHits : List<DrumHit>){
    Box (
        modifier = Modifier
            .background(color)
            .width(width)
            .height(height),
        contentAlignment = Alignment.TopCenter
    )
    {
        for(each in drumHits){
            drumPoint(
                optimalHit = 20.dp,
                tolerance = 20.dp,
                offsetY = each.hitTime.toInt().dp)
        }
    }
}

@Composable
fun showTimer(currentTime: Pair<Long ,Long>, offsetY : Dp){
        Text(
            text = currentTime.first.toString() + "  |  " + currentTime.second.toString(),
            modifier = Modifier.offset(y = offsetY)
        )
}


//@Preview(showBackground = true)
//@Composable
//fun ScreenDrumHeroPreview() {
//    DrumStudyEMSTheme {
//        ScreenDrumHero(Pair(1L, 0L))
//    }
//}

