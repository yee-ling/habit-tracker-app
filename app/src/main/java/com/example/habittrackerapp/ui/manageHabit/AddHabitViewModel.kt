package com.example.habittrackerapp.ui.manageHabit

import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.data.model.Category
import com.example.habittrackerapp.data.model.Frequency
import com.example.habittrackerapp.data.model.Habit
import kotlinx.coroutines.launch
class AddHabitViewModel() : BaseManageViewModel() {
    fun addHabit(name: String, frequency: Frequency, category: Category, count: Int, startDate: Long, endDate: Long?) {
        try {
            val habit = Habit(
                name = name,
                frequency = frequency,
                category = category,
                repeatsPerDay = count,
                startDate = startDate,
                endDate = endDate,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            require(name.isNotBlank()) {"Name cannot be blank"}
            require(endDate == null || endDate >= startDate) {"End date cannot be earlier than start date"}
            repo.add(habit)
            viewModelScope.launch {
                _finish.emit(Unit)
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                _error.emit(e.message.toString())
            }
        }
    }
}