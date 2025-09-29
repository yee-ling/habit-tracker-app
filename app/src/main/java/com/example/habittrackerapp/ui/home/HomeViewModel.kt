package com.example.habittrackerapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.habittrackerapp.data.model.Habit
import com.example.habittrackerapp.data.repo.HabitsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val repo: HabitsRepo = HabitsRepo.getInstance()
) : ViewModel() {
    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits = _habits.asStateFlow()
    fun getHabits() {
        _habits.value = repo.getAllHabits()
    }
}