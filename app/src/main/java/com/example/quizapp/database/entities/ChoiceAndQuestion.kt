package com.example.quizapp.database.entities

import androidx.room.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Question::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("questionId"),
            onDelete = ForeignKey.CASCADE
        )]
)
data class Choice(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val questionId: Long,
    val answer: String,
    val isCorrect: Boolean,
    val type: String,
)

data class ChoiceAndQuestion(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val choice: Choice
)

data class QuestionWithChoices(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val choices: List<Choice>
)
