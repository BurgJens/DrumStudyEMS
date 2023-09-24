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
import model.RythmsEnum
import viewmodel.DrumStudyViewModel


@Composable
fun ScreenStartHandler(
    drumStudyViewModel: DrumStudyViewModel,
    navigateDrumHero : () -> Unit,
    navigateEMSsetup: () -> Unit
){
    val subjectname = drumStudyViewModel.subjectName.collectAsState()

    ScreenStartRender(
        navigateStart = navigateDrumHero,
        subjectname = subjectname.value,
        write = {bool : Boolean -> drumStudyViewModel.logAfter.set(bool)},
        onNameChange = {newName : String-> drumStudyViewModel.changeSubjectName(newName)},
        changeRythm = {rythm : RythmsEnum -> drumStudyViewModel.setRythm(rythm)},
        navigateEMSsetup = navigateEMSsetup
    )
}

@Composable
fun ScreenStartRender(
    navigateStart : () -> Unit,
    write : (Boolean) -> Unit,
    subjectname : String,
    onNameChange : (String) -> Unit,
    changeRythm : (RythmsEnum) -> Unit,
    navigateEMSsetup : ()->Unit
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
                textStyle = TextStyle(textAlign = TextAlign.Center)
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

            for(each in RythmsEnum.values()){
                Button(
                    onClick = {changeRythm(each)},
                    modifier = Modifier
                        .width(200.dp)
                ){
                    Text(each.toString().dropLast(6))
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