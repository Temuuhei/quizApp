package com.example.quizapp.database.daos

import androidx.room.*
import com.example.quizapp.database.entities.Choice

@Dao
interface ChoiceDao {
    @Insert
    fun addChoice(choice: Choice)

    @Query("SELECT * FROM CHOICE ORDER BY id DESC")
    fun getAllChoices(): List<Choice>

    @Update
    fun updateChoice(choice: Choice)

    @Delete
    fun deleteChoice(choice: Choice)

    @Query("DELETE FROM CHOICE")
    fun deleteAll()
}