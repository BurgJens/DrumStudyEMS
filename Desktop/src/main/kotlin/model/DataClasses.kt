package model

enum class LeftRight{LEFT,RIGHT}

data class DrumNote (val segment : Long, val playTime : Long, val timeFrame : Long, val side : LeftRight, var signaled : Boolean = false)

data class DrumHit(val hitTime: Long, val side : LeftRight)

data class RythmManagerData(val timeData: TimeData, val drumNotes : List<DrumNote>, val drumHits : List<DrumHit>, val metronome : List<Long>)

data class RythmNote (val playTime : Long, val timeFrame : Long, val side : LeftRight, var timesAdded : Int = 0)
