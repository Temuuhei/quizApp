package com.example.quizapp.classes

import java.io.Serializable


data class Answer (
    val questionId: Long,
    val answer: String,
    val isCorrect: Boolean
        )
    : Serializable