package com.example.habittrackerapp.ui.manageHabit

import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.data.model.Frequency
import com.example.habittrackerapp.data.model.Habit
import kotlinx.coroutines.launch
class AddHabitViewModel() : BaseManageViewModel() {
    fun addHabit(name: String, frequency: Frequency, count: Int, startDate: Long) {
        val habit = Habit(
            name = name,
            frequency = frequency,
            repeatsPerDay = count,
            startDate = startDate,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        repo.add(habit)
        viewModelScope.launch {
            _finish.emit(Unit)
        }
    }
}