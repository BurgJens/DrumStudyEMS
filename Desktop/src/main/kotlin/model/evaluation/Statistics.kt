package model.evaluation

import model.DrumHit
import model.DrumNote
import model.LeftRight
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import kotlin.math.abs

data class StudyStatistics(
    val group : Int,
    val subject : String,
    val supportMode : String,
    val difficulty : String,
    val task : String,

    val notesToPlay : Int,
    val notesPlayed : Int,
    val averageTimingError  : Int,

    val playedExact : Int,

    val playedEarly : Int,
    val averageEarly : Int,
    val maxEarly : Int,

    val playedLate  : Int,
    val averageLate : Int,
    val maxLate : Int,

    val missedNotes : Int,
    val missedHits : Int,
    val missedBoth : Int,

    val hitPercent : Float,

    //LEFT
    val notesToPlayLeft : Int,
    val notesPlayedLeft : Int,
    val averageTimingErrorLeft : Int,

    val playedExactLeft : Int,

    val playedEarlyLeft : Int,
    val averageEarlyLeft : Int,
    val maxEarlyLeft : Int,

    val playedLateLeft : Int,
    val averageLateLeft  : Int,
    val maxLateLeft : Int,

    val missedNotesLeft : Int,
    val missedHitsLeft : Int,
    val missedBothLeft : Int,

    //LEFT
    val notesToPlayRight : Int,
    val notesPlayedRight : Int,
    val averageTimingErrorRight : Int,

    val playedExactRight : Int,

    val playedEarlyRight : Int,
    val averageEarlyRight : Int,
    val maxEarlyRight : Int,

    val playedLateRight : Int,
    val averageLateRight : Int,
    val maxLateRight : Int,

    val missedNotesRight : Int,
    val missedHitsRight : Int,
    val missedBothRight : Int,
)

fun calcAndLogStatistics(data : EvaluationData, name : String) : StudyStatistics {
    println(data.path)

    val tempNotes = data.drumNotes.toMutableList()
    val tempHits = data.drumHits.toMutableList()

    val (pairedList, unpairedHits) = findMatchingHits(tempNotes, tempHits)

//    for (each in pairedList){
//        println(each.first.playTime)
//        println(each.second?.hitTime)
//        println("")
//    }

    val notesToPlay = pairedList.size
    val notesPlayed = pairedList.count { it.second != null }
    val averageTimingError = calcAverageTimingError(pairedList)

    val playedExact = pairedList.count {it.second != null && it.first.playTime == it.second!!.hitTime}

    val playedEarly = pairedList.count {it.second != null && it.first.playTime > it.second!!.hitTime}
    val (averageEarly,maxEarly) = calcEarly(pairedList)

    val playedLate = pairedList.count {it.second != null && it.first.playTime < it.second!!.hitTime}
    val (averageLate,maxLate) = calcLate(pairedList)

    val missedNotes = pairedList.count { it.second == null }
    val missedHits = unpairedHits.size

    val hitPercent = (notesPlayed.toFloat() / notesToPlay.toFloat())*100


    //LEFT
    val pairedlistLeft = pairedList.filter { it.first.side == LeftRight.LEFT }

    val notesToPlayLeft = pairedlistLeft.size
    val notesPlayedLeft = pairedlistLeft.count { it.second != null }
    val averageTimingErrorLeft = calcAverageTimingError(pairedlistLeft)

    val playedExactLeft = pairedlistLeft.count {it.second != null && it.first.playTime == it.second!!.hitTime}

    val playedEarlyLeft = pairedlistLeft.count {it.second != null && it.first.playTime > it.second!!.hitTime}
    val (averageEarlyLeft,maxEarlyLeft) = calcEarly(pairedlistLeft)

    val playedLateLeft = pairedlistLeft.count {it.second != null && it.first.playTime < it.second!!.hitTime}
    val (averageLateLeft,maxLateLeft) = calcLate(pairedlistLeft)

    val missedNotesLeft = pairedlistLeft.count { it.second == null }
    val missedHitsLeft = unpairedHits.filter { it.side == LeftRight.LEFT }.size


    //LEFT
    val pairedlistRight = pairedList.filter { it.first.side == LeftRight.RIGHT }

    val notesToPlayRight = pairedlistRight.size
    val notesPlayedRight = pairedlistRight.count { it.second != null }
    val averageTimingErrorRight = calcAverageTimingError(pairedlistRight)

    val playedExactRight = pairedlistRight.count {it.second != null && it.first.playTime == it.second!!.hitTime}

    val playedEarlyRight = pairedlistRight.count {it.second != null && it.first.playTime > it.second!!.hitTime}
    val (averageEarlyRight,maxEarlyRight) = calcEarly(pairedlistRight)

    val playedLateRight = pairedlistRight.count {it.second != null && it.first.playTime < it.second!!.hitTime}
    val (averageLateRight,maxLateRight) = calcLate(pairedlistRight)

    val missedNotesRight = pairedlistRight.count { it.second == null }
    val missedHitsRight = unpairedHits.filter { it.side == LeftRight.RIGHT }.size

    var statsLog =  "\n\n"

    statsLog += "Notes to play:\t\t\t${notesToPlay}\n" +
                "Notes played:\t\t\t${notesPlayed}\n" +
                "Average timing error:\t\t${averageTimingError}\n\n" +
                "Notes hit percent:\t\t${hitPercent}\n\n" +

                "Exact hit :\t\t\t${playedExact}\n\n" +

                "Amount early:\t\t\t${playedEarly}\n" +
                "Average early:\t\t\t${averageEarly}\n" +
                "Max early:\t\t\t${maxEarly}\n\n" +

                "Amount late:\t\t\t${playedLate}\n" +
                "Average late:\t\t\t${averageLate}\n" +
                "Max late:\t\t\t${maxLate}\n\n" +

                "Notes missed:\t\t\t${missedNotes}\n" +
                "Missed hits:\t\t\t${missedHits}\n\n\n" +


                "LEFT SIDE\n\n" +

                "Notes to play Left:\t\t${notesToPlayLeft}\n" +
                "Notes played Left:\t\t${notesPlayedLeft}\n" +
                "Average timing error Left:\t${averageTimingErrorLeft}\n\n" +

                "Exact hit Left:\t\t\t${playedExactLeft}\n\n" +

                "Amount early Left:\t\t${playedEarlyLeft}\n" +
                "Average early Left:\t\t${averageEarlyLeft}\n" +
                "Max early Left:\t\t\t${maxEarlyLeft}\n\n" +

                "Amount late Left:\t\t${playedLateLeft}\n" +
                "Average late Left:\t\t${averageLateLeft}\n" +
                "Max late Left:\t\t\t${maxLateLeft}\n\n" +

                "Notes missed Left:\t\t${missedNotesLeft}\n" +
                "Missed hits Left:\t\t${missedHitsLeft}\n\n\n" +


                "RIGHT SIDE\n\n" +

                "Notes to play Right:\t\t${notesToPlayRight}\n" +
                "Notes played Right:\t\t${notesPlayedRight}\n" +
                "Average timing error Right:\t${averageTimingErrorRight}\n\n" +

                "Exact hit Right:\t\t${playedExactRight}\n\n" +

                "Amount early Right:\t\t${playedEarlyRight}\n" +
                "Average early Right:\t\t${averageEarlyRight}\n" +
                "Max early Right:\t\t${maxEarlyRight}\n\n" +

                "Amount late Right:\t\t${playedLateRight}\n" +
                "Average late Right:\t\t${averageLateRight}\n" +
                "Max late Right:\t\t\t${maxLateRight}\n\n" +

                "Notes missed Right:\t\t${missedNotesRight}\n" +
                "Missed hits Right:\t\t${missedHitsRight}\n\n\n"

    val statistics = StudyStatistics(
        getGroupNumber(data.path),
        getSubjectNumber(data.path),
        when (data.path.contains("EMS")){
            true -> "EMS"
            false -> "Audio"
        },
        if (data.path.contains("ER" )) "easy"
        else if (data.path.contains("EL")) "easy"
        else if (data.path.contains("MR")) "medium"
        else if (data.path.contains("ML")) "medium"
        else if (data.path.contains("SR")) "hard"
        else "hard",
        if (data.path.contains("ER")) "ER"
        else if (data.path.contains("EL")) "EL"
        else if (data.path.contains("MR")) "MR"
        else if (data.path.contains("ML")) "ML"
        else if (data.path.contains("SR")) "SR"
        else "SL",

        notesToPlay,
        notesPlayed,
        averageTimingError,

        playedExact,

        playedEarly,
        averageEarly,
        maxEarly,

        playedLate,
        averageLate,
        maxLate,

        missedNotes,
        missedHits,
        missedNotes+missedHits,

        hitPercent,

        //LEFT
        notesToPlayLeft,
        notesPlayedLeft,
        averageTimingErrorLeft,

        playedExactLeft,

        playedEarlyLeft,
        averageEarlyLeft,
        maxEarlyLeft,

        playedLateLeft,
        averageLateLeft,
        maxLateLeft,

        missedNotesLeft,
        missedHitsLeft,
        missedNotesLeft+missedHitsLeft,


        //LEFT
        notesToPlayRight,
        notesPlayedRight,
        averageTimingErrorRight,

        playedExactRight,

        playedEarlyRight,
        averageEarlyRight,
        maxEarlyRight,

        playedLateRight,
        averageLateRight,
        maxLateRight,

        missedNotesRight,
        missedHitsRight,
        missedNotesRight+missedHitsRight,
    )

    val path = data.path.substringBeforeLast("\\")
    val fileName = data.path.substringAfterLast("\\") + name + ".txt"


    val directory = File(path)
    val file = File(directory, fileName)

    try {
        if (!directory.exists()) directory.mkdirs()

        if (!file.exists()) file.createNewFile()

        val fileWriter = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(statsLog)
        bufferedWriter.close()

        println("data written successfully: ${file.absolutePath}")
    } catch (e: Exception) {
        println("erorr writing: ${e.message}")
    }

    return statistics
}

fun findMatchingHits(notes: List<DrumNote>, hits: List<DrumHit>): Pair<List<Pair<DrumNote, DrumHit?>>, List<DrumHit>> {
    val pairedNotesHits = mutableListOf<Pair<DrumNote, DrumHit?>>()
    val unpairedHits = hits.toMutableList()

    for (note in notes) {
        val matchingHit = hits.find {
            it.side == note.side && Math.abs(it.hitTime - note.playTime) <= note.timeFrame / 2
        }

        if (matchingHit != null) {
            pairedNotesHits.add(note to matchingHit)
            unpairedHits.remove(matchingHit)
        } else {
            pairedNotesHits.add(note to null)
        }
    }

    return pairedNotesHits to unpairedHits
}

fun calcAverageTimingError(pairedList : List<Pair<DrumNote, DrumHit?>>) : Int{
    var timingError = 0
    var hit = 0

    for(each in pairedList){
        if (each.second != null){
            hit ++
            timingError += abs((each.first.playTime - each.second!!.hitTime).toInt())
        }
    }

    if(hit == 0) return -1000
    return (timingError / hit)
}

fun calcEarly(pairedList : List<Pair<DrumNote, DrumHit?>>) : Pair<Int,Int>{
    var timingError = 0
    var earlyCount = 0
    var maxEarly = 0

    for(each in pairedList){
        if (each.second != null){
            if(each.second!!.hitTime < each.first.playTime){
                earlyCount++
                val error = abs((each.first.playTime - each.second!!.hitTime).toInt())
                timingError += error
                if (error > maxEarly) maxEarly = error
            }
        }

    }
    return timingError/earlyCount to maxEarly
}

fun calcLate(pairedList : List<Pair<DrumNote, DrumHit?>>) : Pair<Int,Int>{
    var timingError = 0
    var lateCount = 0
    var maxLate = 0

    for(each in pairedList){
        if (each.second != null){
            if(each.second!!.hitTime > each.first.playTime){
                lateCount++
                val error = abs((each.first.playTime - each.second!!.hitTime).toInt())
                timingError += error
                if (error > maxLate) maxLate = error
            }
        }

    }
    return timingError/lateCount to maxLate
}

fun getGroupNumber(path : String): Int{
    val regex = Regex("Gruppe_(\\d+)")
    val matchResult = regex.find(path)

    val ret = matchResult?.groups?.get(1)?.value?.toIntOrNull()
    return ret ?: 0
}

fun getSubjectNumber(path : String): String{
    val regex = Regex("Subject_(\\d+)")
    val matchResult = regex.find(path)

    var ret = matchResult?.groups?.get(1)?.value?.toIntOrNull().toString()
    if (ret.length == 1) ret = "0" + ret
    return ret
}