package model

import com.example.drumstudyems.model.Rythm
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import kotlin.math.abs
import kotlin.math.absoluteValue

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

    fun logData(subjectName : String, activeRythm : Rythm, drumNotes : List<DrumNote>, drumHits : List<DrumHit>){

        logDrumnotes(subjectName, activeRythm.name, drumNotes)
        logDrumHits(subjectName, activeRythm.name, drumHits)

        logStatistics(subjectName, activeRythm,drumNotes,drumHits)

    }

    fun logDrumnotes(subjectName : String, rythmName : String, drumNotes : List<DrumNote>){
        var log = String()
        for (each in drumNotes){
            log += "${each.side} ${each.playTime}\n"
        }
        writeLog("${subjectName}/${subjectName}_${rythmName}", "${subjectName}_${rythmName}_drumNotes",log )
    }

    fun logDrumHits(subjectName : String, rythmName : String, drumNotes : List<DrumHit>){
        var log = String()
        for (each in drumNotes){
            log += "${each.side} ${each.hitTime}\n"
        }
        writeLog("${subjectName}/${subjectName}_${rythmName}", "${subjectName}_${rythmName}_drumHits",log )
    }

    fun logStatistics(subjectName : String, activeRythm : Rythm, drumNotes : List<DrumNote>, drumHits : List<DrumHit>){
        val tempNotes = drumNotes.toMutableList()
        val tempHits = drumHits.toMutableList()

        val pairedList = mutableListOf<Pair<DrumNote, DrumHit?>>()
        val unpairedHits = mutableListOf<DrumHit>()

        for (note in tempNotes) {
            val hitsSameSideAndInRange = tempHits.filter {
                it.side == note.side
                        &&
                (note.playTime-it.hitTime).absoluteValue <= note.timeFrame/2
            }
            if (hitsSameSideAndInRange.isNotEmpty()) {
                val closestHit = hitsSameSideAndInRange.minByOrNull { Math.abs(it.hitTime - note.playTime) }
                pairedList.add(Pair(note,closestHit))
                tempHits.remove(closestHit)
            }else{pairedList.add(Pair(note,null))}
        }
        unpairedHits.addAll(tempHits)

        var generalCount = 0
        var generalValue = 0L
        var generalAverage = 0L

        var lateCount = 0
        var lateValue = 0L
        var lateAverage = 0L
        var lateMax = 0L

        var earlyCount = 0
        var earlyValue = 0L
        var earlyAverage = 0L
        var earlyMax = 0L

        var notCount = 0

        for (each in pairedList){
            if (each.second == null){
                notCount++
            }else {
                generalCount++
                val timeDifference = abs(each.first.playTime - each.second!!.hitTime)
                 generalValue += timeDifference
                if (timeDifference > 0){
                    earlyCount++
                    earlyValue += abs(timeDifference)
                    if (abs(timeDifference) > earlyMax) earlyMax = abs(timeDifference)
                } else{
                    lateCount++
                    lateValue += abs(timeDifference)
                    if (abs(timeDifference) > lateMax) lateMax = abs(timeDifference)
                }
            }
        }


        if (generalCount == 0) {
            return
        }
        lateAverage = lateValue / lateCount
        generalAverage = generalValue / generalCount
        earlyAverage = earlyValue / earlyCount


        val statsLog =  "${subjectName}s statistics\n\n" +

                        "Notes to play:\t\t${pairedList.size}\n" +
                        "Notes played:\t\t${generalCount}\n" +
                        "Average timing error:\t${generalAverage}\n\n" +

                        "Amount early:\t\t${earlyCount}\n" +
                        "Average early:\t\t${earlyAverage}\n" +
                        "Max early:\t\t${earlyMax}\n\n" +

                        "Amount late:\t\t${lateCount}\n" +
                        "Average late:\t\t${lateAverage}\n" +
                        "Max late:\t\t${lateMax}\n\n" +

                        "Notes missed:\t\t${notCount}\n" +
                        "Missed hits:\t\t${unpairedHits.size}\n"

        writeLog("${subjectName}/${subjectName}_${activeRythm.name}", "${subjectName}_${activeRythm.name}_statistics",statsLog )
    }
}