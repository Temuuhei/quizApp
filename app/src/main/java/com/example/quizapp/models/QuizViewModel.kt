package com.example.quizapp.models


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.classes.Answer
import com.example.quizapp.database.entities.QuestionWithChoices

class QuizViewModel : ViewModel() {
    private var _correctAnswerCounter = MutableLiveData(0)
    var correctAnswerCounter: LiveData<Int> = _correctAnswerCounter

    var questions: List<QuestionWithChoices> = ArrayList()
    private var answers: List<Answer> = ArrayList()

    private var _currentIndex = MutableLiveData(0)
    var currentIndex: LiveData<Int> = _currentIndex

    private var answersLiveData = MutableLiveData<List<Answer>>()

    fun reset() {
        _currentIndex.value = 0
        _correctAnswerCounter.value = 0
    }

    fun getAnswers(): MutableLiveData<List<Answer>> {
        answersLiveData.value = answers
        return answersLiveData
    }

//    fun setAnswerByQuestionId(questionId: Long, answer: String) {
//        var question = questions.stream()
//            .filter { q -> q.question.id == questionId }
//            .findFirst()
//            .orElse(null)
//
//        if(question != null) {
//            var answer = answers.stream()
//                .filter { a -> a.questionId == questionId }
//                .findFirst()
//                .orElse(null)
//
//            if(answer != null) {
//                print("Answered already")
//            } else {
//                var correctChoices = question.choices.stream()
//                    .filter { c -> c.isCorrect }
//                    .findAny()
//                    .orElse(null)
//
//                print(correctChoices)
//
//                //SELECTION OPTION
//                if(question.choices.size > 0) {
////                    var answer = Answer(questionId, answer, question.choices.)
//                } else {
////                    var answer = Answer(questionId, answer, question.question.co)
//
//                }
//            }
//        } else {
//            print("Question not found")
//        }
//    }

    fun addCorrectScore() {
        _correctAnswerCounter.value = _correctAnswerCounter.value!! + 1
    }

    fun getCurrentQuestion(): QuestionWithChoices {
        return questions[_currentIndex.value!!]
    }

    fun goNext() {
        if (_currentIndex.value!! < questions.size) {
            _currentIndex.value = _currentIndex.value!! + 1
        } else {
            _currentIndex.value = 0
        }
    }
}