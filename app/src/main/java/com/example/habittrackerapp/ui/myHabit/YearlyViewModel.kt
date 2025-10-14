package com.example.habittrackerapp.ui.myHabit

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habittrackerapp.MyApp
import com.example.habittrackerapp.data.model.Frequency
import com.example.habittrackerapp.data.repo.HabitsRepoRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class YearlyViewModel(repo: HabitsRepoRoom) : BaseFrequencyViewModel(repo) {
    init {
        getHabits()
    }
    override fun getHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllHabits().map { habits ->
                habits.filter { it.frequency == Frequency.YEARLY }
            }.collect { yearlyHabits ->
                _habits.value = yearlyHabits
            }
        }
    }
    override fun search(search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllHabits().map { habits ->
                habits.filter { it.frequency == Frequency.YEARLY }
                    .filter { it.name.contains(search) }
            }.collect { search ->
                _habits.value = search
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApp).repo
                YearlyViewModel(myRepository)
            }
        }
    }
}