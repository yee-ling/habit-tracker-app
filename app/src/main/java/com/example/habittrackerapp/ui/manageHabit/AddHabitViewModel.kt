package com.example.habittrackerapp.ui.manageHabit

import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.data.model.Frequency
import com.example.habittrackerapp.data.model.Habit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class AddHabitViewModel() : BaseManageViewModel() {
    private val _count = MutableStateFlow(1)
    val count = _count.asStateFlow()
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
    fun increment() {
        _count.value++
    }
    fun decrement() {
        if(_count.value > 1) {
            _count.value--
        }
    }
}