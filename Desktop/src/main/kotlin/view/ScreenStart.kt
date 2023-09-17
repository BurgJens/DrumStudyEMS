package view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import viewmodel.DrumStudyViewModel


@Composable
fun ScreenStartHandler(
    drumStudyViewModel: DrumStudyViewModel,
    navigateFirst : () -> Unit
){
    val subjectname = drumStudyViewModel.subjectName.collectAsState()

    ScreenStartRender(
        navigateStart = navigateFirst,
        subjectname = subjectname.value,
        write = {drumStudyViewModel.write()},
        onNameChange = {newName : String-> drumStudyViewModel.changeSubjectName(newName)}
    )
}

@Composable
fun ScreenStartRender(
    navigateStart : () -> Unit,
    write : () -> Unit,
    subjectname : String,
    onNameChange : (String) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        var subjectName by remember { mutableStateOf(subjectname) }
        Button(
            onClick = {
                onNameChange(subjectName)
                navigateStart()
            }
        ){
            Text(text = "Start")
        }

        Button(
            onClick = write
        ){
            Text(text = "Write")
        }


        TextField(
            value = subjectName,
            onValueChange = {subjectName = it}
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun ScreenStartPreview() {
//    DrumStudyEMSTheme {
//        ScreenStartRender({})
//    }
//}