package com.mosiuk.funnycombination.ui.viewmodel

import HighScoreViewModel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.mosiuk.funnycombination.data.AppDatabase
import com.mosiuk.funnycombination.data.HighScoreRepository

object DI {
    @Volatile private var db: AppDatabase? = null

    fun provideDb(context: Context): AppDatabase =
        db ?: synchronized(this) {
            db ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "funny_combination.db"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { db = it }
        }

    fun provideHighScoreRepo(context: Context) =
        HighScoreRepository(provideDb(context).highScoreDao())
}

class HighScoreViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HighScoreViewModel::class.java)) {
            val repo = DI.provideHighScoreRepo(context)
            return HighScoreViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}
