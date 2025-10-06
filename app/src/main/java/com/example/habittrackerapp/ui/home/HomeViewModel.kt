package com.example.habittrackerapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.habittrackerapp.data.model.Habit
import com.example.habittrackerapp.data.repo.HabitsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class HomeViewModel(
    private val repo: HabitsRepo = HabitsRepo.getInstance()
) : ViewModel() {
    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    val habits = _habits.asStateFlow()
//    fun getHabits() {
//        _habits.value = repo.getAllHabits()
//    }
//fun getHabits(selectedDate: Long) {
//    _habits.value = repo.getAllHabits().filter {
//        it.startDate <= selectedDate
//    }
//}

    fun getHabits(selectedDate: Long) {
        _habits.value = repo.getAllHabits().filter { it ->
            val habitStart = Calendar.getInstance().apply {
                timeInMillis = it.startDate
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

//            habitStart == selectedDate

            val start = (habitStart <= selectedDate)
            val withinEnd = it.endDate?.let { selectedDate <= it } ?: true
            start && withinEnd
        }
    }
}