package com.example.quizapp.database.entities

import androidx.room.*

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val question: String
){
    constructor(question: String) : this(0, question)
}

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Question::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("questionId"),
            onDelete = ForeignKey.CASCADE
        )]
)
data class Answer(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val answer: String,
    val questionId: Long,
    val isCorrect: Boolean
){
    constructor(answer: com.example.quizapp.classes.Answer) : this(0, answer.answer, answer.questionId, answer.isCorrect)
}

data class AnswerAndQuestion(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val answer: Answer
)
