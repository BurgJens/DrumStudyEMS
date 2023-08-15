package com.example.drumstudyems.model



class DrumHitManager {

    val activeHits = mutableListOf<DrumHit>()

    val oldHits = mutableListOf<DrumHit>()

    fun addHit(hit : DrumHit){
        activeHits.add(hit)
    }


}