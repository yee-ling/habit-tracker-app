package com.example.habittrackerapp.ui.myHabit

import androidx.lifecycle.ViewModel
import com.example.habittrackerapp.data.model.Habit
import com.example.habittrackerapp.data.repo.HabitsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseFrequencyViewModel(
    protected val repo: HabitsRepo = HabitsRepo.getInstance()
) : ViewModel() {
    protected val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits = _habits.asStateFlow()
    abstract fun getHabits()
    abstract fun search(search: String)
}