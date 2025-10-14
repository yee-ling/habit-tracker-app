package com.example.habittrackerapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.habittrackerapp.data.model.Habit
import com.example.habittrackerapp.data.model.HabitWithProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitsDao {
    @Query("SELECT * FROM Habit")
    fun getAllHabits(): Flow<List<Habit>>
    @Query("SELECT * FROM Habit")
    fun getAllHabitsWithProgress(): Flow<List<HabitWithProgress>>
    @Query("SELECT * FROM habit WHERE id = :id")
    suspend fun getHabitById(id: Int): Habit?
    @Query("SELECT * FROM habit WHERE id = :id")
    suspend fun getHabitByIdWithProgress(id: Int): HabitWithProgress?
    @Insert
    suspend fun addHabit(habit: Habit)
    @Update
    suspend fun update(habit: Habit)
    @Query("DELETE FROM habit WHERE id = :id")
    suspend fun delete(id: Int)
}