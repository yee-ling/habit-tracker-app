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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditHabitViewModel(repo: HabitsRepoRoom) : BaseManageViewModel(repo) {
    private val _habit = MutableStateFlow<Habit?>(null)
    val habit = _habit.asStateFlow()
    fun getHabitById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val habit = repo.getHabitById(id)
            _habit.value = habit
            _count.value = habit?.repeatsPerDay ?:1
        }
    }
    fun updateHabit(id: Int, name: String, frequency: Frequency, category: Category, count: Int, startDate: Long, endDate: Long?) {
        require(name.isNotBlank()) { "Name cannot be blank" }
        require(endDate == null || endDate >= startDate) { "End date cannot be earlier than start date" }
        viewModelScope.launch(Dispatchers.IO) {
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
                    createdAt = habit?.createdAt ?: System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                repo.updateHabit(habit = updatedHabit)
                withContext(Dispatchers.Main) {
                    _finish.emit(Unit)
                }
            } catch (e: Exception) {
                viewModelScope.launch {
                    _error.emit(e.message.toString())
                }
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                EditHabitViewModel(myRepository)
            }
        }
    }
}