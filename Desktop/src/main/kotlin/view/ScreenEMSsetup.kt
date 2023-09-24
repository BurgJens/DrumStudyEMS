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

    ScreenEMSsetupRender(
        navigateBack,
        {cmd : String -> drumStudyViewModel.sendEMScommand(cmd)}
    )
}

@Composable
fun ScreenEMSsetupRender(
    navigateBack : () -> Unit,
    sendCMD : (String) -> Unit
){

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var channel by remember { mutableStateOf("1") }
        var intensity by remember { mutableStateOf("010") }
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
            onClick = {sendCMD("C${channel.toInt()-1}I${intensity}T${duration}G")},
            modifier = Modifier
                .width(200.dp)
        ){
            Text(text = "Start")
        }
        Button(
            onClick = navigateBack,
            modifier = Modifier
                .width(200.dp)
        ){
            Text(text = "Back")
        }
    }
}