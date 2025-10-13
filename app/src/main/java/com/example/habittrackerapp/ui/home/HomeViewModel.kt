package com.example.habittrackerapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habittrackerapp.MyApp
import com.example.habittrackerapp.data.model.HabitWithProgress
import com.example.habittrackerapp.data.repo.HabitsRepoRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeViewModel(
    private val repo: HabitsRepoRoom
) : ViewModel() {
    private val _habits = MutableStateFlow<List<HabitWithProgress>>(emptyList())
    val habits = _habits.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO)  {
            repo.getAllHabitsWithProgress().collect { _habits.value = it }
        }
    }
    fun getHabits(selectedDate: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllHabitsWithProgress().map { habits ->
                habits.filter { it ->
                    val habitStart = Calendar.getInstance().apply {
                        timeInMillis = it.habit.startDate
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }.timeInMillis
                    val startDate = (habitStart <= selectedDate)
                    val untilEndDate = it.habit.endDate?.let { selectedDate <= it } ?: true
                    startDate && untilEndDate
                }
            }
                .collect { filteredHabits ->
                    _habits.value = filteredHabits

                }
            }
        }
    fun updateProgress(habitId: Int, date: Long, count: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateProgress(habitId = habitId, date = date, count = count)
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                HomeViewModel(myRepository)
            }
        }
    }
}