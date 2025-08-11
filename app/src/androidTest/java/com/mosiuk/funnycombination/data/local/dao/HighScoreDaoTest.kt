package com.mosiuk.funnycombination.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mosiuk.funnycombination.data.local.AppDatabase
import com.mosiuk.funnycombination.data.local.entity.HighScoreEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class HighScoreDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: HighScoreDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.highScoreDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetBestScore() = runTest {
        val score = HighScoreEntity(date = "11.08.2025", score = 10)
        dao.insertScore(score)
        val bestScore = dao.getBestScore()
        assertEquals(10, bestScore)
    }

    @Test
    @Throws(Exception::class)
    fun getBestScore_noScores_returnsNull() = runTest {
        val bestScore = dao.getBestScore()
        assertNull(bestScore)
    }

    @Test
    @Throws(Exception::class)
    fun getTopScores_returnsScoresInDescOrder() = runTest {
        dao.insertScore(HighScoreEntity(date = "d1", score = 5))
        dao.insertScore(HighScoreEntity(date = "d2", score = 15))
        dao.insertScore(HighScoreEntity(date = "d3", score = 10))

        val topScores = dao.getTopScores()
        assertEquals(3, topScores.size)
        assertEquals(15, topScores[0].score)
        assertEquals(10, topScores[1].score)
        assertEquals(5, topScores[2].score)
    }
}
