package com.example.habittrackerapp.ui.manageHabit

import androidx.lifecycle.ViewModel
import com.example.habittrackerapp.data.model.Habit
import com.example.habittrackerapp.data.repo.HabitsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HabitDetailsViewModel(
    private val repo: HabitsRepo = HabitsRepo.getInstance()
) : ViewModel() {
    private val _habit = MutableStateFlow<Habit?>(null)
    val habit = _habit.asStateFlow()

    fun getHabitById(id: Int) {
        _habit.value = repo.getHabitById(id)
    }
}