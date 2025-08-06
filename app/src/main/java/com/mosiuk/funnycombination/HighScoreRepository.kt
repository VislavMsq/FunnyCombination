package com.mosiuk.funnycombination

import android.os.Build
import androidx.annotation.RequiresApi

object HighScoreRepository {
    private val _scores = mutableListOf<HighScore>()
    val scores: List<HighScore> get() = _scores

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun add(score: HighScore) {
        _scores.add(score)
        _scores.sortByDescending { it.score }
        if (_scores.size > 10) {
            _scores.removeLast()
        }
    }
}