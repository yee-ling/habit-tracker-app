package com.example.habittrackerapp.data.repo

import com.example.habittrackerapp.data.model.Habit

class HabitsRepo private constructor() {
    val map = mutableMapOf<Int, Habit>()
    var counter = 0
    fun add(habit: Habit) {
        map[++counter] = habit.copy(id = counter)
    }
    fun getHabitById(id: Int): Habit? {
        return map[id]
    }
    fun getAllHabits() = map.values.toList()
    fun deleteHabit(id: Int) {
        map.remove(id)
    }
    fun updateHabit(id: Int, habit: Habit) {
        map[id] = habit
    }
    fun isCompleted(id: Int) {
        map[id]?.let { habit ->
            map[id] = habit.copy(isCompleted = true)
        }
    }
    companion object {
        private var instance: HabitsRepo? = null
        fun getInstance(): HabitsRepo {
            if(instance == null) {
                instance = HabitsRepo()
            }
            return instance!!
        }
    }
}