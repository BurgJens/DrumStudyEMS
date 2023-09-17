package model

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class LogWriter {
    fun logData(folerName : String,fileName : String, data : String) {

        val data = data
        val path = "logs/$folerName"
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

    fun convertRythmManagerData() : String{


        return ""
    }
}