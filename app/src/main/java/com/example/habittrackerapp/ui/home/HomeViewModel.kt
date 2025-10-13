package com.example.habittrackerapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.data.model.HabitWithProgress
import com.example.habittrackerapp.data.repo.HabitsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeViewModel(
    private val repo: HabitsRepo = HabitsRepo.getInstance()
) : ViewModel() {
    private val _habits = MutableStateFlow<List<HabitWithProgress>>(emptyList())
    val habits = _habits.asStateFlow()
    fun getHabits(selectedDate: Long) {
        _habits.value = repo.getAllHabitsWithProgress().filter { it ->
            val habitStart = Calendar.getInstance().apply {
                timeInMillis = it.habit.startDate
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            val startDate = (habitStart <= selectedDate)
            val untilEndDate = it.habit.endDate?.let { selectedDate <= it } ?: true
            startDate && untilEndDate
        }
    }
    fun updateProgress(habitId: Int, date: Long, count: Int) {
        viewModelScope.launch {
            repo.updateProgress(habitId = habitId, date = date, count = count)
            _habits.value = repo.getAllHabitsWithProgress()
        }
    }
    fun addRecord() {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        repo.getAllHabits().forEach { habit ->
            val habitExist = repo.getHabitWithProgressByDate(habit.id!!, today)
            if(habitExist == null) {
                repo.updateProgress(habit.id, today, 0)
            }
        }
    }
}