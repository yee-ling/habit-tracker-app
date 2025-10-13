package com.example.habittrackerapp.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class HabitWithProgress(
    @Embedded val habit: Habit,
    @Relation(
        parentColumn = "id",
        entityColumn = "habitId"
    )
    val progress: List<Progress>
)
