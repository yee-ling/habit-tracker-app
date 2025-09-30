package com.example.habittrackerapp.data.model

data class Habit(
    val id: Int? = null,
    val name: String,
    val frequency: Frequency = Frequency.DAILY,
    val repeatsPerDay: Int = 1,
    val startDate: Long,
    val endDate: Long? = null,
    val isCompleted: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
)
enum class Frequency {
    DAILY, WEEKLY, MONTHLY, YEARLY
}