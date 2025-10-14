package com.example.habittrackerapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val frequency: Frequency = Frequency.DAILY,
    val category: Category = Category.PERSONAL,
    val repeatsPerDay: Int = 1,
    val startDate: Long,
    val endDate: Long? = null,
    val createdAt: Long,
    val updatedAt: Long
)
enum class Frequency {
    DAILY, WEEKLY, MONTHLY, YEARLY
}
enum class Category {
    HEALTH, STUDY, WORK, PERSONAL
}