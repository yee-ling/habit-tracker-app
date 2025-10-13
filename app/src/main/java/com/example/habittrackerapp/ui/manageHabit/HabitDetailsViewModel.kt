package com.example.habittrackerapp.ui.manageHabit

import androidx.lifecycle.ViewModel
import com.example.habittrackerapp.data.model.HabitWithProgress
import com.example.habittrackerapp.data.repo.HabitsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HabitDetailsViewModel(
    private val repo: HabitsRepo = HabitsRepo.getInstance()
) : ViewModel() {
    private val _habit = MutableStateFlow<HabitWithProgress?>(null)
    val habit = _habit.asStateFlow()
    fun getHabitByIdWithProgressByDate(id: Int, date: Long) {
        _habit.value = repo.getHabitByIdWithProgressByDate(id, date)
    }
    fun deleteHabit(id: Int) {
        repo.deleteHabit(id)
    }
    fun getCurrentStreak(id: Int): Int {
        val allProgress = repo.getProgressListForHabit(id)
            .filter { it.isCompleted }
            .sortedByDescending { it.date }
        if(allProgress.isEmpty()) return 0
        var currentStreak = 1
        var latestDate = allProgress.first().date
        for(i in 1 until allProgress.size) {
            val currentDate = allProgress[i].date
            val diff = (latestDate - currentDate) / (1000*60*60*24)
            if(diff.toInt() == 1) {
                currentStreak++
                latestDate = currentDate
            } else {
                break
            }
        }
        return currentStreak
    }
    fun getLongestStreak(id: Int): Int {
        val allProgress = repo.getProgressListForHabit(id)
            .filter { it.isCompleted }
            .sortedByDescending { it.date }
        if(allProgress.isEmpty()) return 0
        var currentStreak = 1
        var longestStreak = 1
        var latestDate = allProgress.first().date
        for(i in 1 until allProgress.size) {
            val currentDate = allProgress[i].date
            val diff = (latestDate - currentDate) / (1000*60*60*24)
            if(diff.toInt() == 1) {
                currentStreak++
            } else {
                currentStreak = 1
            }
            longestStreak = maxOf(currentStreak, longestStreak)
            latestDate = currentDate
        }
        return longestStreak
    }
}