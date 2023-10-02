package model

import javazoom.jl.player.advanced.AdvancedPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.FileInputStream

class Beeper() {
    fun playSound(side: LeftRight) {
        CoroutineScope(Dispatchers.Default).launch {
            val filePath = when (side) {
                LeftRight.LEFT -> "audio/beepL.mp3"
                LeftRight.RIGHT -> "audio/beepR.mp3"
            }
            try {
                val inputStreamL = FileInputStream(filePath)
                val playerL = AdvancedPlayer(inputStreamL)

                playerL.play()
                playerL.close()
            }catch (e: Exception) {
                println("Fehler beim Abspielen der Datei: ${e.message}")
            }
        }
    }
}