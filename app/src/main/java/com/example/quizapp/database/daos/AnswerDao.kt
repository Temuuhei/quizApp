package com.example.quizapp.database.daos

import androidx.room.*
import com.example.quizapp.database.entities.Answer

@Dao
interface AnswerDao {
    @Insert
    fun addAnswer(answer: Answer)

    @Query("SELECT * FROM ANSWER ORDER BY id DESC")
    fun getAllAnswers(): List<Answer>

    @Update
    fun updateAnswer(answer: Answer)

    @Delete
    fun deleteAnswer(answer: Answer)

    @Query("DELETE FROM ANSWER")
    fun deleteAll()
}