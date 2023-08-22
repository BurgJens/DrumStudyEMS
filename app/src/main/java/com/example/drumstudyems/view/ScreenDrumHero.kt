package com.example.drumstudyems.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drumstudyems.model.LeftRight
import com.example.drumstudyems.model.RythmManagerData
import com.example.drumstudyems.model.TimeData
import com.example.drumstudyems.viewmodel.DrumStudyViewModel
import kotlinx.coroutines.Dispatchers



@Composable
fun ScreenDrumHeroData(drumStudyViewModel: DrumStudyViewModel){

    val rythmManagerData by
    drumStudyViewModel.getRythmManagerData()
        .collectAsState(initial = RythmManagerData(
            timeData = TimeData(0L,0L),
            drumHits = listOf()
        ), Dispatchers.Main)

//    val test by
//    drumStudyViewModel.startTimer().collectAsState(initial = TimeData(0L,0L))

    ScreenDrumHero(
        rythmManagerData = rythmManagerData,
        timeFrame = 5000L,
        debug = true,
        optimalHitTimeFrame = 200L,
        hitTimeTolerance = 100L,
        {drumStudyViewModel.debugButtonInpug(rythmManagerData.timeData.currentTime)}
    )
}

@Composable
fun ScreenDrumHero(
    rythmManagerData: RythmManagerData,
    timeFrame: Long,
    debug : Boolean,
    optimalHitTimeFrame : Long,
    hitTimeTolerance : Long,
    debugButtonInput : () -> Unit
){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    )
    {

        RythmBox(
            Color.White,
            screenWidth,
            screenHeight,
            rythmManagerData,
            timeFrame,
            debug,
            optimalHitTimeFrame,
            hitTimeTolerance)

        Divider(
            modifier = Modifier
                .height(5.dp)
                .offset(y = -screenHeight / 3 + 5.dp/2)
                .alpha(0.5f),
            color = Color.Black,
        )
        if (debug){
            Text(
                text = "${rythmManagerData.timeData.currentTime}",
                modifier = Modifier
                    .offset(y = -screenHeight / 3),
                color = Color.LightGray
            )
            val buttonHeight = screenHeight/20
            OutlinedButton(
                onClick = debugButtonInput,
                modifier = Modifier
                    .width(screenWidth)
                    .offset(y = -screenHeight / 3 + buttonHeight/2)
                    .height(buttonHeight)
                    .alpha(0.5f),
            ){}
        }
        
        ShowTimer(rythmManagerData.timeData, screenHeight/100*95)
    }

}

@Composable
fun RythmBox(
    color : Color,
    screenWidth : Dp,
    screenHeight : Dp,
    rythmManagerData : RythmManagerData,
    timeFrame: Long,
    debug: Boolean,
    optimalHitTimeFrame : Long,
    hitTimeTolerance : Long
    ){
    val upperEdge = rythmManagerData.timeData.currentTime + (timeFrame/3*2)
    val lowerEdge = rythmManagerData.timeData.currentTime - (timeFrame/3)

    val optimalHitSize = screenHeight.times(optimalHitTimeFrame.toFloat() / timeFrame.toFloat())
    val hitToleranceSize = screenHeight.times(hitTimeTolerance.toFloat() / timeFrame.toFloat())

    Box (
        modifier = Modifier
            .background(color)
            .width(screenWidth)
            .height(screenHeight),
        contentAlignment = Alignment.TopCenter
    )
    {
        for(each in rythmManagerData.drumHits){
            val t = (each.hitTime - upperEdge).toFloat() / (lowerEdge - upperEdge).toFloat()
            val posY = screenHeight.times(t)
            drumPoint(
                optimalHit = optimalHitSize,
                tolerance = hitToleranceSize,
                offsetX = screenWidth / 4,
                offsetY = posY,
                side = each.side,
                debug = debug,
                hitTime = each.hitTime)

        }
    }
}


@Composable
fun ShowTimer(timeData: TimeData, offsetY : Dp){
        Text(
            text = "${timeData.currentTime}  |  ${timeData.deltaTime}",
            modifier = Modifier.offset(y = offsetY),
            fontSize = 30.sp
        )
}


@Composable
fun drumPoint (
    optimalHit : Dp,
    tolerance : Dp,
    offsetY : Dp,
    offsetX : Dp,
    side : LeftRight,
    debug : Boolean,
    hitTime : Long
){
    val side = when(side){
        LeftRight.RIGHT -> 1
        LeftRight.LEFT -> -1
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.
        offset(y = offsetY- optimalHit/2 - tolerance, x = offsetX*side)
    ){
        toleranceLine(optimalHit, tolerance)
        drumCircle(optimalHit)
        if (debug){
            Text(
                text = "$hitTime",
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun drumCircle (optimalHit: Dp){
    Canvas(
        modifier = Modifier
            .width(optimalHit)
            .height(optimalHit)
    ) {
        drawCircle(Color.Blue, radius = (optimalHit/2).toPx())
    }
}

@Composable
fun toleranceLine (optimalHit : Dp, tolerance: Dp){
    Divider(
        modifier = Modifier
            .height(optimalHit + tolerance * 2)
            .width(5.dp),
        color = Color.Red,

        )
}


//@Preview(showBackground = true)
//@Composable
//fun ScreenDrumHeroPreview() {
//    DrumStudyEMSTheme {
//        ScreenDrumHero(Pair(1L, 0L))
//    }
//}

