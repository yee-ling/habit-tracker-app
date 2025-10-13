package com.example.habittrackerapp.ui.manageHabit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habittrackerapp.MyApp
import com.example.habittrackerapp.data.model.HabitWithProgress
import com.example.habittrackerapp.data.repo.HabitsRepoRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HabitDetailsViewModel(
    private val repo: HabitsRepoRoom
) : ViewModel() {
    private val _habit = MutableStateFlow<HabitWithProgress?>(null)
    val habit = _habit.asStateFlow()
    fun getHabitByIdWithProgressByDate(id: Int, date: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _habit.value = repo.getHabitByIdWithProgressByDate(id, date)
        }
    }
    fun deleteHabit(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
        repo.deleteHabit(id)
        }
    }
    suspend fun getCurrentStreak(id: Int): Int {
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
    suspend fun getLongestStreak(id: Int): Int {
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
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                HabitDetailsViewModel(myRepository)
            }
        }
    }
}