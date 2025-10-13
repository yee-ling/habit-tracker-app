package com.example.habittrackerapp.ui.myHabit

import androidx.lifecycle.viewModelScope
import com.example.habittrackerapp.data.model.Frequency
import kotlinx.coroutines.launch

class DailyViewModel: BaseFrequencyViewModel() {
    init {
        getHabits()
    }
    override fun getHabits() {
        _habits.value = repo.getAllHabits().filter { it.frequency == Frequency.DAILY }
    }

    override fun search(search: String) {
        viewModelScope.launch {
            _habits.value = repo.getAllHabits()
                .filter { it.frequency == Frequency.DAILY }
                .filter { it.name.contains(search) }
        }
    }
}