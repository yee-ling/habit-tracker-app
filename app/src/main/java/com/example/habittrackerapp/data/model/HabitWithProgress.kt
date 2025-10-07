package com.example.habittrackerapp.data.model

data class HabitWithProgress(
    val habit: Habit,
    val progress: List<Progress>
)
