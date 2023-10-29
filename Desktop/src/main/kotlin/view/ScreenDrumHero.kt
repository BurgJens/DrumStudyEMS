package view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import AppDebugMode
import AppTimeFrame
import kotlinx.coroutines.Dispatchers
import viewmodel.DrumStudyViewModel
import AppWindowSize
import androidx.compose.desktop.ui.tooling.preview.Preview
import model.*


@Composable
fun ScreenDrumHeroData(
    drumStudyViewModel: DrumStudyViewModel,
    navigateBack : () -> Unit,
){
    val rythmManagerData by drumStudyViewModel.getRythmManagerData()
        .collectAsState(
            initial = RythmManagerData(
                timeData = TimeData(0L,0L),
                drumNotes = listOf(),
                drumHits = listOf(),
                metronome = listOf()
            ), Dispatchers.Main
        )

    ScreenDrumHero(
        rythmManagerData = rythmManagerData,
        timeFrame = AppTimeFrame,
        optimalHitTimeFrame = 200L,
        {drumStudyViewModel.debugButtonInpug(rythmManagerData.timeData.currentTime)},
        navigateBack = {
            drumStudyViewModel.stopDrumListener()
            navigateBack()
        }
    )
}

@Composable
fun ScreenDrumHero(
    rythmManagerData: RythmManagerData,
    timeFrame: Long,
    optimalHitTimeFrame : Long,
    debugButtonInput : () -> Unit,
    navigateBack : () -> Unit,
){

    val screenSize = AppWindowSize
    val screenHeight = screenSize.height
    val screenWidth = screenSize.width

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
        )
        if (AppDebugMode){
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

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {navigateBack()}
            ){ Text("Stop") }
        }

    }

}

@Composable
fun RythmBox(
    color : Color,
    screenWidth : Dp,
    screenHeight : Dp,
    rythmManagerData : RythmManagerData,
    timeFrame: Long
    ){
    val upperEdge = rythmManagerData.timeData.currentTime + (timeFrame/3*2)
    val lowerEdge = rythmManagerData.timeData.currentTime - (timeFrame/3)

    Box (
        modifier = Modifier
            .background(color)
            .width(screenWidth/3)
            .height(screenHeight),
        contentAlignment = Alignment.TopCenter
    ){
        Card(
            modifier = Modifier
                .fillMaxSize(),
            border = BorderStroke(2.dp,Color.Black),
            backgroundColor = Color.White
        ) {

        }
        CurrentTimeLine(rythmManagerData, upperEdge, lowerEdge)
        for(each in rythmManagerData.drumNotes){
            val t = inverseLerp(lowerEdge.toFloat(),upperEdge.toFloat(),each.playTime.toFloat())
            val posY = screenHeight.times(t)
            MusicNote(
                //timeFrame = each.timeFrame.toInt().dp,
                timeFrame = screenHeight.times(each.timeFrame.toInt() / timeFrame.toFloat()),
                offsetX = 150.dp,
                offsetY = posY,
                side = each.side,
                hitTime = each.playTime)

        }
        for(each in rythmManagerData.drumHits){
            val t = inverseLerp(lowerEdge.toFloat(),upperEdge.toFloat(),each.hitTime.toFloat())
            val posY = screenHeight.times(t)
            DrumHit(
                offsetX = 150.dp,
                offsetY = posY,
                side = each.side
            )
        }
        Metronome(rythmManagerData, upperEdge, lowerEdge)
    }

}

@Composable
fun Metronome(rythmManagerData : RythmManagerData, upperEdge : Long, lowerEdge : Long){

    for (each in rythmManagerData.metronome) {

        val t = inverseLerp(lowerEdge.toFloat(),upperEdge.toFloat(),each.toFloat())
        val posY = AppWindowSize.height.times(t)

        Divider(
            modifier = Modifier
                .height(2.dp)
                .offset(y = posY)
                .alpha(0.5f),
            color = Color.Blue,
        )
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
fun MusicNote (
    timeFrame : Dp,
    offsetY : Dp,
    offsetX : Dp,
    side : LeftRight,
    hitTime : Long,
){
    val side = when(side){
        LeftRight.RIGHT -> 1
        LeftRight.LEFT -> -1
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.
        offset(y = offsetY- timeFrame/2, x = offsetX*side)
    ){
        DrumHitCircle(timeFrame)
        if (AppDebugMode){
            Text(
                text = "$hitTime",
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun DrumHit(
    offsetY : Dp,
    offsetX : Dp,
    side : LeftRight,
){
    val side = when(side){
        LeftRight.RIGHT -> 1
        LeftRight.LEFT -> -1
    }
    Divider(
        modifier = Modifier
            .offset(y = offsetY, x = offsetX * side)
            .width(75.dp)
            .height(2.dp),
        color = Color.Black
    )

}

@Composable
fun DrumHitCircle (optimalHit: Dp){
    Canvas(
        modifier = Modifier
            .width(optimalHit)
            .height(optimalHit)
    ) {
        drawCircle(Color.Blue, radius = (optimalHit/2).toPx())
    }
}

@Composable
fun CurrentTimeLine(rythmManagerData : RythmManagerData,  upperEdge : Long, lowerEdge : Long){

    val t = inverseLerp(lowerEdge.toFloat(), upperEdge.toFloat(), rythmManagerData.timeData.currentTime.toFloat())

    val posY = AppWindowSize.height.times(t)

    if (AppDebugMode) ShowTimer(timeData = rythmManagerData.timeData, offsetY = posY)

    Divider(
        modifier = Modifier
            .height(2.dp)
            .offset(y = posY)
            .alpha(0.5f),
        color = Color.Red,
    )
}


@Preview
@Composable
fun ScreenDrumHeroPreview() {
    val speedMult = 1.5

    val rythmManagerData = RythmManagerData(
        timeData = TimeData(
            currentTime = 1000L,
            deltaTime = 4L
        ),
        drumNotes = listOf(
            DrumNote(1,(0 * speedMult).toLong(),124L,LeftRight.LEFT, false),
            DrumNote(1,(500 * speedMult).toLong(),124L,LeftRight.RIGHT, false),

            DrumNote(1,(1000 * speedMult).toLong(),124L,LeftRight.LEFT, false),
            DrumNote(1,(1500 * speedMult).toLong(),124L,LeftRight.RIGHT, false),

            DrumNote(1,(2000 * speedMult).toLong(),124L,LeftRight.LEFT, false),
            DrumNote(1,(2250 * speedMult).toLong(),124L,LeftRight.LEFT, false),
            DrumNote(1,(2500 * speedMult).toLong(),124L,LeftRight.RIGHT, false),
            DrumNote(1,(2750 * speedMult).toLong(),124L,LeftRight.RIGHT, false),

            DrumNote(1,(3000 * speedMult).toLong(),124L,LeftRight.LEFT, false),
            DrumNote(1,(3250 * speedMult).toLong(),124L,LeftRight.LEFT, false),
            DrumNote(1,(3500 * speedMult).toLong(),124L,LeftRight.RIGHT, false)
        ),
        drumHits = listOf(
            DrumHit(950L, LeftRight.LEFT),
            DrumHit(20L, LeftRight.LEFT),
            DrumHit((500 * speedMult).toLong()-40L, LeftRight.RIGHT)
        ),
        metronome = listOf(0L)
    )
    val timeFrame = 6000L // Beispielwert für timeFrame
    val optimalHitTimeFrame = 124L // Beispielwert für optimalHitTimeFrame

    ScreenDrumHero(
        rythmManagerData = rythmManagerData,
        timeFrame = timeFrame,
        optimalHitTimeFrame = optimalHitTimeFrame,
        debugButtonInput = {},
        navigateBack = {}
    )
}