package com.example.habittrackerapp.ui.manageHabit

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habittrackerapp.MyApp
import com.example.habittrackerapp.data.model.Category
import com.example.habittrackerapp.data.model.Frequency
import com.example.habittrackerapp.data.model.Habit
import com.example.habittrackerapp.data.repo.HabitsRepoRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class AddHabitViewModel(repo: HabitsRepoRoom) : BaseManageViewModel(repo) {
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
            viewModelScope.launch(Dispatchers.IO) {
                repo.add(habit)
                _finish.emit(Unit)
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                _error.emit(e.message.toString())
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                AddHabitViewModel(myRepository)
            }
        }
    }
}