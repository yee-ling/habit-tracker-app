package com.example.habittrackerapp.ui.myHabit

import com.example.habittrackerapp.data.model.Frequency

class DailyViewModel: BaseFrequencyViewModel() {
    init {
        getHabits()
    }
    override fun getHabits() {
        _habits.value = repo.getAllHabits().filter { it.frequency == Frequency.DAILY }
    }
}