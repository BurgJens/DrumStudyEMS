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
                val inputStream = FileInputStream(filePath)
                val player = AdvancedPlayer(inputStream)

                player.play()
                player.close()
            }catch (e: Exception) {
                println("Error playing: ${e.message}")
            }
        }
    }
}