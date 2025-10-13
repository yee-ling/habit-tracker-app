package com.example.habittrackerapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "progress",
    indices = [Index(value = ["habitId", "date"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Progress(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val habitId: Int,
    val date: Long,
    val progress: Int = 0,
    val isCompleted: Boolean = false
)
