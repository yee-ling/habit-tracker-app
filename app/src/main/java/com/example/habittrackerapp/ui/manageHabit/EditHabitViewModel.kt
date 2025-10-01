package com.example.habittrackerapp.ui.manageHabit

import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.data.model.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditHabitViewModel() : BaseManageViewModel() {
//    private var _habit = MutableStateFlow(Habit(
//    name = "",
//    frequency = Frequency.DAILY,
//    repeatsPerDay = 1,
//    startDate = 0L,
//    endDate = 0L,
//    isCompleted = false,
//    createdAt = 0L,
//    updatedAt = 0L
//))
    private val _habit = MutableStateFlow<Habit?>(null)
    val habit = _habit.asStateFlow()

    fun getHabitById(id: Int) {
        viewModelScope.launch {
            val habit = repo.getHabitById(id)
            _habit.value = habit
            _count.value = habit?.repeatsPerDay ?:1
        }
    }
}