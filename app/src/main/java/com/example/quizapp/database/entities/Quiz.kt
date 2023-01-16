package com.example.quizapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
)
