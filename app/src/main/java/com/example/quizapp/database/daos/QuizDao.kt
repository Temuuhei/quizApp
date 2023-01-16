package com.example.quizapp.database.daos


import androidx.room.*
import com.example.quizapp.database.entities.Quiz

@Dao
interface QuizDao {
    @Insert
    fun addQuiz(quiz: Quiz)

    @Query("SELECT * FROM QUIZ ORDER BY id DESC")
    fun getAllQuizzes(): List<Quiz>

    @Update
    fun updateQuiz(quiz: Quiz)

    @Delete
    fun deleteQuiz(quiz: Quiz)

    @Query("DELETE FROM QUIZ")
    fun deleteAll()
}