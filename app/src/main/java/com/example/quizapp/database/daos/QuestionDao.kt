package com.example.quizapp.database.daos

import androidx.room.*
import com.example.quizapp.database.entities.Question
import com.example.quizapp.database.entities.QuestionWithChoices

@Dao
interface QuestionDao {
    @Insert
    fun addQuestion(question: Question): Long

    @Query("SELECT * FROM QUESTION ORDER BY id DESC")
    fun getAllQuestions(): List<Question>

    @Query("SELECT * FROM QUESTION")
    fun getQuestionWithChoices(): List<QuestionWithChoices>

    @Update
    fun updateQuestion(question: Question)

    @Delete
    fun deleteQuestion(question: Question)

    @Query("DELETE FROM QUESTION")
    fun deleteAll()
}