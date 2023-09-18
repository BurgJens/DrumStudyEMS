package model

enum class LeftRight{LEFT,RIGHT}

data class DrumNote (var segment : Long, var playTime : Long, var side : LeftRight)

data class DrumHit(var hitTime: Long, var side : LeftRight)

data class RythmManagerData(val timeData: TimeData, val drumNotes : List<DrumNote>, val drumHits : List<DrumHit>, val metronome : List<Long>)
