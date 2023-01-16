package com.example.quizapp.classes

data class Question(
    val id: Long,
    val question: String,
    val choices: Array<Choice>
) : java.io.Serializable {

}
