package com.example.habittrackerapp.data.model

data class Progress(
    val id: Int? = null,
    val habitId: Int,
    val date: Long,
    val progress: Int = 0,
    val isCompleted: Boolean = false
)
