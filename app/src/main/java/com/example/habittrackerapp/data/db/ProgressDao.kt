package com.example.habittrackerapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.habittrackerapp.data.model.Progress

@Dao
interface ProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(progress: Progress)
//    @Query("UPDATE progress SET progress = :count, isCompleted = :isCompleted WHERE habitId = :habitId AND date = :date")
//    fun updateProgress(habitId: Int, date: Long, count: Int, isCompleted: Boolean): Int
    @Query("SELECT * FROM progress WHERE habitId = :habitId AND date = :date LIMIT 1")
    suspend fun getProgressForDate(habitId: Int, date: Long): Progress?
    @Query("SELECT * FROM progress WHERE habitId = :habitId ORDER by date DESC")
    suspend fun getProgressForHabit(habitId: Int): List<Progress>
}