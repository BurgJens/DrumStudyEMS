package view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import viewmodel.DrumStudyViewModel

@Composable
fun ScreenEMSsetup(drumStudyViewModel: DrumStudyViewModel, navigateBack : () -> Unit){

    val startIntensity = drumStudyViewModel.emsIntensityMax.collectAsState()

    ScreenEMSsetupRender(
        startIntensity = startIntensity.value,
        navigateBack = navigateBack,
        saveCurrentSettings = {value : Int ->
            drumStudyViewModel.changeEmsIntensityMax(value)
        },
        sendCMD = {cmd : String -> drumStudyViewModel.sendEMScommand(cmd)}
    )
}

@Composable
fun ScreenEMSsetupRender(
    startIntensity : Int,
    navigateBack : () -> Unit,
    saveCurrentSettings : (Int) -> Unit,
    sendCMD : (String) -> Unit
){

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var channel by remember { mutableStateOf("1") }
        var intensity by remember { mutableStateOf("$startIntensity") }
        var duration by remember { mutableStateOf("1000") }

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Channel")
                TextField(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(10.dp),
                    value = channel,
                    onValueChange = { channel = it },
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            )  {
                Text("Intensity")
                TextField(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(10.dp),
                    value = intensity,
                    onValueChange = { intensity = it },
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            )  {
                Text("Duration")
                TextField(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(10.dp),
                    value = duration,
                    onValueChange = { duration = it },
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )
            }
        }
        Button(
            onClick = {
                sendCMD("C${channel.toInt()-1}I${intensity}T${duration}G")
                saveCurrentSettings(intensity.toInt())
                      },
            modifier = Modifier
                .width(200.dp)
        ){
            Text(text = "Start")
        }
        Button(
            onClick = {
                saveCurrentSettings(intensity.toInt())
                navigateBack()
            },
            modifier = Modifier
                .width(200.dp)
        ){
            Text(text = "Back")
        }
    }
}