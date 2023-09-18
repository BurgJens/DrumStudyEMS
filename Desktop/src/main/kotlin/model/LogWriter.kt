package model

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class LogWriter {
    fun writeLog(folderName : String, fileName : String, data : String) {

        val data = data
        val path = "logs/$folderName"
        val fileName = "$fileName.txt"

        val directory = File(path)
        val file = File(directory, fileName)

        try {
            if (!directory.exists()) directory.mkdirs()

            if (!file.exists()) file.createNewFile()

            val fileWriter = FileWriter(file)
            val bufferedWriter = BufferedWriter(fileWriter)
            bufferedWriter.write(data)
            bufferedWriter.close()

            println("Daten erfolgreich geschrieben: ${file.absolutePath}")
        } catch (e: Exception) {
            println("Fehler beim Schreiben in die Datei: ${e.message}")
        }
    }

    fun logData(subjectName : String, rythmName : String, drumNotes : List<DrumNote>, drumHits : List<DrumHit>){

        logDrumnotes(subjectName, rythmName, drumNotes)
        logDrumHits(subjectName, rythmName, drumHits)

    }

    fun logDrumnotes(subjectName : String, rythmName : String, drumNotes : List<DrumNote>){
        var log = String()
        for (each in drumNotes){
            log += "${each.side} ${each.playTime}\n"
        }
        writeLog(subjectName, "${subjectName}_${rythmName}_drumNotes",log )
    }

    fun logDrumHits(subjectName : String, rythmName : String, drumNotes : List<DrumHit>){
        var log = String()
        for (each in drumNotes){
            log += "${each.side} ${each.hitTime}\n"
        }
        writeLog(subjectName, "${subjectName}_${rythmName}_drumHits",log )
    }

    fun createStatistics(drumNotes : List<DrumNote>, drumHits : List<DrumHit>){
        val tempNotes = drumNotes.toList()
        val tempHits = drumHits.toList()

        for (note in drumNotes){
            var nearest : Long? = null

            for (hit in drumHits){
                if (hit.hitTime >= note.playTime+751L) break
            }
        }

    }
}