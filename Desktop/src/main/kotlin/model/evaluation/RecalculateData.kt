package model.evaluation


import TimeFrameToHit
import androidx.compose.ui.window.application
import model.DrumHit
import model.DrumNote
import model.LeftRight
import org.apache.poi.ss.usermodel.*
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.reflect.full.primaryConstructor

data class EvaluationData(val path : String, val drumNotes : List<DrumNote>, val drumHits : List<DrumHit>)

fun main() = application {

    val txtDateien = getDrumHitsPaths("logs")

    val evaluationData = mutableListOf<EvaluationData>()

    // Ausgabe der gesammelten Pfade
    for (pfad in txtDateien) {
        val drumNotes = pfad.dropLast(12).plus("drumNotes.txt")
        evaluationData += EvaluationData(
            "logs" + pfad.dropLast(12).substringAfter("logs"),
            parseDrumNotesFromFile(drumNotes, TimeFrameToHit),
            parseDrumHitsFromFile(pfad)

        )
    }

    val statistics = mutableListOf<StudyStatistics>()

    for(each in evaluationData) {
        statistics.add(calcAndLogStatistics(each, "statistics_01"))
    }

    for (each in statistics) println(each)

    val sortedList = statistics.sortedWith(compareBy(
        { it.task },
        { it.supportMode },
        { it.group }
    ))

    toExcel(sortedList)

}


fun getDrumHitsPaths(ordnerpfad: String) : MutableList<String>{
    val drumHitsPaths = mutableListOf<String>()
    val folder = File(ordnerpfad)
    val data = folder.listFiles()

    if (data != null) {
        for (datei in data) {
            if (datei.isFile && datei.name.endsWith("drumHits.txt")) {
                drumHitsPaths.add(datei.absolutePath)
            } else if (datei.isDirectory) {
                drumHitsPaths += getDrumHitsPaths(datei.absolutePath)
            }
        }
    }
    return drumHitsPaths
}

fun parseDrumHitsFromFile(dateipfad: String): List<DrumHit> {
    val drumHits = mutableListOf<DrumHit>()

    File(dateipfad).forEachLine { line ->
        val tokens = line.split(" ")
        if (tokens.size == 2) {
            val side = if (tokens[0].toUpperCase() == "LEFT") LeftRight.LEFT else LeftRight.RIGHT
            val hitTime = tokens[1].toLongOrNull()
            if (hitTime != null) {
                drumHits.add(DrumHit(hitTime, side))
            } else {
                println("Ungültige Zeile: $line")
            }
        } else {
            println("Ungültige Zeile: $line")
        }
    }

    return drumHits
}

fun parseDrumNotesFromFile(filePath: String, timeFrame: Long): List<DrumNote> {
    val drumNotes = mutableListOf<DrumNote>()
    val lines = File(filePath).readLines()

    var segment = 0L
    var playTime = 0L

    lines.forEachIndexed { index, line ->
        val splitStingList = line.split(" ")
        if (splitStingList.size == 2) {
            val side = if (splitStingList[0].uppercase(Locale.getDefault()) == "LEFT") LeftRight.LEFT else LeftRight.RIGHT
            val hitTime = splitStingList[1].toLongOrNull()
            if (hitTime != null) {
                val timeInSegment = hitTime - playTime
                drumNotes.add(DrumNote(segment, hitTime, TimeFrameToHit, side))
                playTime = hitTime
            } else {
                println("Ungültige Zeile in Zeile ${index + 1}: $line")
            }
        } else {
            println("Ungültige Zeile in Zeile ${index + 1}: $line")
        }
    }
    return drumNotes
}


fun toExcel(data : List<StudyStatistics>){
    val workbook: Workbook = WorkbookFactory.create(true)

    val sheet: Sheet = workbook.createSheet("Mein Arbeitsblatt")

    var rowNum = 0

    val propertyNames = StudyStatistics::class.primaryConstructor!!.parameters
        .map { it.name }

    val headerRow: Row = sheet.createRow(rowNum++)
    var colNum = 0
    for (each in propertyNames) {
        val cell: Cell = headerRow.createCell(colNum++)
        cell.setCellValue(each)
    }

    for (rowData in data) {
        if (rowData.subject != "06"){
            val row: Row = sheet.createRow(rowNum++)
            var colNum = 0

            val fields = rowData.javaClass.declaredFields
            for (field in fields) {
                field.isAccessible = true
                val value = field.get(rowData)

                val cell: Cell = row.createCell(colNum++)
                when (field.type) {
                    String::class.java -> cell.setCellValue(value as String)
                    Int::class.java -> cell.setCellValue((value as Int).toDouble())
                    Float::class.java -> cell.setCellValue((value as Float).toDouble())
                    Boolean::class.java -> cell.setCellValue(value as Boolean)
                    else -> cell.setCellValue("Nicht unterstützter Datentyp")
                }
            }
        }
    }

    val excelFile = FileOutputStream("output.xlsx")
    workbook.write(excelFile)
    excelFile.close()

}