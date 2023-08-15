package com.example.drumstudyems

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.drumstudyems.ui.theme.DrumStudyEMSTheme
import com.example.drumstudyems.view.AppNavigation
import com.example.drumstudyems.viewmodel.DrumStudyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val drumStudyViewModel : DrumStudyViewModel by viewModels()

        setContent {
            DrumStudyEMSTheme {
                AppNavigation(drumStudyViewModel)
            }
        }
    }
}