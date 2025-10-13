package com.example.habittrackerapp.data.repo

import com.example.habittrackerapp.data.db.HabitsDao
import com.example.habittrackerapp.data.db.ProgressDao
import com.example.habittrackerapp.data.model.Habit
import com.example.habittrackerapp.data.model.HabitWithProgress
import com.example.habittrackerapp.data.model.Progress
import kotlinx.coroutines.flow.Flow

class HabitsRepoRoom(
    private val dao: HabitsDao,
    private val progressDao: ProgressDao
) {
    suspend fun add(habit: Habit) {
        dao.addHabit(habit)
    }
    suspend fun getHabitById(id: Int): Habit? {
        return dao.getHabitById(id)
    }
    fun getAllHabits(): Flow<List<Habit>> {
        return dao.getAllHabits()
    }
    fun getAllHabitsWithProgress(): Flow<List<HabitWithProgress>> {
        return dao.getAllHabitsWithProgress()
    }
    suspend fun deleteHabit(id: Int) {
        dao.delete(id)
    }
    suspend fun updateHabit(habit: Habit) {
        dao.update(habit)
    }
    suspend fun updateProgress(habitId: Int, date: Long, count: Int) {
        val habit = dao.getHabitById(habitId)
        val repeatsPerDay = habit?.repeatsPerDay?:1
        val isCompleted = count >= repeatsPerDay

        val progress = Progress(
            habitId = habitId,
            date = date,
            progress = count,
            isCompleted = isCompleted
        )
        progressDao.insertOrUpdate(progress)
    }
    suspend fun getHabitWithProgressByDate(habitId: Int, date: Long): Progress? {
        return progressDao.getProgressForDate(habitId, date)
    }
    suspend fun getHabitByIdWithProgressByDate(habitId: Int, date: Long): HabitWithProgress? {
        val habit = dao.getHabitById(habitId) ?: return null
        val progress = progressDao.getProgressForDate(habitId, date)
        val progressList = progress?.let { listOf(it) } ?: emptyList()
        return HabitWithProgress(habit, progressList)
    }
    suspend fun getProgressListForHabit(habitId: Int): List<Progress> {
        return progressDao.getProgressForHabit(habitId)
    }
}