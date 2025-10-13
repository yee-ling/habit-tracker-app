package com.example.habittrackerapp.data.repo

import com.example.habittrackerapp.data.model.Habit
import com.example.habittrackerapp.data.model.HabitWithProgress
import com.example.habittrackerapp.data.model.Progress

class HabitsRepo private constructor() {
    val map = mutableMapOf<Int, Habit>()
    val progressMap = mutableMapOf<Int, MutableList<Progress>>()
    var counter = 0
    var progressCounter = 0
    fun add(habit: Habit) {
        counter++
        map[counter] = habit.copy(id = counter)
    }
    fun getHabitById(id: Int): Habit? {
        return map[id]
    }
    fun getAllHabits() = map.values.toList()
    fun getAllHabitsWithProgress(): List<HabitWithProgress> {
        return map.values.map { habit ->
            HabitWithProgress(
                habit = habit,
                progress = progressMap[habit.id] ?: emptyList()
            )
        }
    }
    fun deleteHabit(id: Int) {
        map.remove(id)
        progressMap.remove(id)
    }
    fun updateHabit(id: Int, habit: Habit) {
        map[id] = habit.copy(id = id)
    }
    fun updateProgress(habitId: Int, date: Long, count: Int) {
        val progressList = progressMap[habitId] ?: mutableListOf()
        val progress = progressList.find { it.date == date }
        if (progress != null) {
            progressList[progressList.indexOf(progress)] = progress.copy(
                progress = count,
                isCompleted = count == (map[habitId]?.repeatsPerDay ?: 1)
            )
        } else {
            val id = ++progressCounter
            progressList.add(
                Progress(id = id, habitId = habitId, date = date, progress = count, isCompleted = count == (map[habitId]?.repeatsPerDay ?: 1))
            )
        }
        progressMap[habitId] = progressList
    }
    fun getHabitWithProgressByDate(habitId: Int, date: Long): Progress? {
        return progressMap[habitId]?.find { it.date == date }
    }
    fun getHabitByIdWithProgressByDate(habitId: Int, date: Long): HabitWithProgress? {
        val habit = map[habitId] ?: return null
        val progress = getHabitWithProgressByDate(habitId, date)
        val progressList = progress?.let { listOf(it) } ?: emptyList()
        return HabitWithProgress(habit, progressList)
    }
    fun getProgressListForHabit(habitId: Int): List<Progress> {
        return progressMap[habitId] ?: emptyList()
    }
    companion object {
        private var instance: HabitsRepo? = null
        fun getInstance(): HabitsRepo {
            if(instance == null) {
                instance = HabitsRepo()
            }
            return instance!!
        }
    }
}