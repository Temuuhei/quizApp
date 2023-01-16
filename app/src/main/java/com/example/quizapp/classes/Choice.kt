package com.example.quizapp.classes

data class Choice(
    val id: Long,
    val answer: String,
    val type: ChoiceType,
    val isCorrect: Boolean
) : java.io.Serializable

enum class ChoiceType {
    One,
    Many,
    Text
}
