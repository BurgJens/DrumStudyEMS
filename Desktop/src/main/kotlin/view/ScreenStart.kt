package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import model.RythmsEnum
import viewmodel.DrumStudyViewModel
import viewmodel.SupportMode


@Composable
fun ScreenStartHandler(
    drumStudyViewModel: DrumStudyViewModel,
    navigateDrumHero : () -> Unit,
    navigateEMSsetup: () -> Unit
){
    val subjectname = drumStudyViewModel.subjectName.collectAsState()
    val activeRythmFlow = drumStudyViewModel.activeRythmFlow.collectAsState()
    val supportMode = drumStudyViewModel.supportMode.collectAsState()

    ScreenStartRender(
        navigateStart = {
            drumStudyViewModel.startDrumListener()
            navigateDrumHero()
                        },
        subjectname = subjectname.value,
        write = {bool : Boolean ->
            drumStudyViewModel.setLogMode(bool)},
        onNameChange = {newName : String->
            drumStudyViewModel.changeSubjectName(newName)
                       },
        changeRythm = {rythm : RythmsEnum ->
            drumStudyViewModel.setRythm(rythm)
                      },
        changeSupportMode = {mode : SupportMode ->
            drumStudyViewModel.setSupportMode(mode)
        },
        navigateEMSsetup = navigateEMSsetup,
        activeRythmFlow = activeRythmFlow.value,
        supportMode = supportMode.value
    )
}

@Composable
fun ScreenStartRender(
    navigateStart : () -> Unit,
    write : (Boolean) -> Unit,
    subjectname : String,
    onNameChange : (String) -> Unit,
    changeRythm : (RythmsEnum) -> Unit,
    changeSupportMode : (SupportMode) -> Unit,
    navigateEMSsetup : ()->Unit,
    activeRythmFlow : RythmsEnum,
    supportMode : SupportMode
){
    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Button(
            onClick = navigateEMSsetup
        ){
            Text("Test EMS")
        }

        Column(
            modifier = Modifier
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            var subjectName by remember { mutableStateOf(subjectname) }
            TextField(
                modifier = Modifier
                    .width(200.dp),
                value = subjectName,
                onValueChange = {subjectName = it},
                textStyle = TextStyle(textAlign = TextAlign.Center),
            )
            Button(
                onClick = {
                    write(false)
                    onNameChange(subjectName)
                    navigateStart()
                },
                modifier = Modifier
                    .width(200.dp)
            ){
                Text(text = "Start")
            }
            Button(
                onClick = {
                    write(true)
                    onNameChange(subjectName)
                    navigateStart()
                },
                modifier = Modifier
                    .width(200.dp)
            ){
                Text(text = "Start and Log")
            }
        }

        Column(
            modifier = Modifier
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ){
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = "Rythms"
            )

            for(each in RythmsEnum.entries){
                Button(
                    onClick = {changeRythm(each)},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = when(activeRythmFlow){
                            each -> MaterialTheme.colors.primary
                            else -> Color.White
                    }),
                    modifier = Modifier
                        .width(200.dp)
                ){
                    Text(each.toString().dropLast(6))
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ){
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = "Modes"
            )

            for(each in SupportMode.entries){
                Button(
                    onClick = {changeSupportMode(each)},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = when(supportMode){
                            each -> MaterialTheme.colors.primary
                            else -> Color.White
                        }),
                    modifier = Modifier
                        .width(200.dp)
                ){
                    Text(each.toString())
                }
            }
        }
    }

}


//@Preview(showBackground = true)
//@Composable
//fun ScreenStartPreview() {
//    DrumStudyEMSTheme {
//        ScreenStartRender({})
//    }
//}