package com.example.habittrackerapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habittrackerapp.data.model.Habit
import com.example.habittrackerapp.data.model.Progress

@Database(
    entities = [Habit::class, Progress::class],
    version = 1
)
abstract class MyDatabase: RoomDatabase() {
    abstract fun getHabitsDao(): HabitsDao
    abstract fun getProgressDao(): ProgressDao
    companion object {
        const val NAME = "my_database"
    }
}