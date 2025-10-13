package com.example.habittrackerapp.ui.manageHabit

import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.data.model.Category
import com.example.habittrackerapp.data.model.Frequency
import com.example.habittrackerapp.data.model.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditHabitViewModel() : BaseManageViewModel() {
    private val _habit = MutableStateFlow<Habit?>(null)
    val habit = _habit.asStateFlow()
    fun getHabitById(id: Int) {
        viewModelScope.launch {
            val habit = repo.getHabitById(id)
            _habit.value = habit
            _count.value = habit?.repeatsPerDay ?:1
        }
    }
    fun updateHabit(id: Int, name: String, frequency: Frequency, category: Category, count: Int, startDate: Long, endDate: Long?) {
        try {
            val habit = repo.getHabitById(id)
            val updatedHabit = Habit(
                id = id,
                name = name,
                frequency = frequency,
                category = category,
                repeatsPerDay = count,
                startDate = startDate,
                endDate = endDate,
                createdAt = habit?.createdAt?:System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            require(name.isNotBlank()) {"Name cannot be blank"}
            require(endDate == null || endDate >= startDate) {"End date cannot be earlier than start date"}
            repo.updateHabit(id = id, habit = updatedHabit)
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