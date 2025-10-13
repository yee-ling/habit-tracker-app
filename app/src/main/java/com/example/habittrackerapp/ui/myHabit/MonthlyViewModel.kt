package com.example.habittrackerapp.ui.myHabit

import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.data.model.Frequency
import kotlinx.coroutines.launch

class MonthlyViewModel : BaseFrequencyViewModel() {
    init {
        getHabits()
    }
    override fun getHabits() {
        _habits.value = repo.getAllHabits().filter { it.frequency == Frequency.MONTHLY }
    }
    override fun search(search: String) {
        viewModelScope.launch {
            _habits.value = repo.getAllHabits()
                .filter { it.frequency == Frequency.MONTHLY }
                .filter { it.name.contains(search) }
        }
    }
}