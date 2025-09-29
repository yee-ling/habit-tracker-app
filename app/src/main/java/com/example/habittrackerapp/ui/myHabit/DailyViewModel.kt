package com.example.habittrackerapp.ui.myHabit

import androidx.lifecycle.ViewModel
import com.example.habittrackerapp.data.model.Frequency
import com.example.habittrackerapp.data.model.Habit
import com.example.habittrackerapp.data.repo.HabitsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DailyViewModel(
    private val repo: HabitsRepo = HabitsRepo.getInstance()
) : ViewModel() {
    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits = _habits.asStateFlow()
    init {
        getHabits()
    }
    fun getHabits() {
        _habits.value = repo.getAllHabits().filter { it.frequency == Frequency.DAILY }
    }
}