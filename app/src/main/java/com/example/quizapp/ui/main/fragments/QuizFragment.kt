package com.example.quizapp.ui.main.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.allViews
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.quizapp.R
import com.example.quizapp.classes.Answer
import com.example.quizapp.classes.ChoiceType
import com.example.quizapp.database.QuizDatabase
import com.example.quizapp.database.entities.QuestionWithChoices
import com.example.quizapp.models.QuizViewModel
import kotlinx.android.synthetic.main.fragment_quiz.*
import kotlinx.android.synthetic.main.fragment_quiz.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class QuizFragment : Fragment() {

    companion object {
        fun newInstance() = QuizFragment()
    }

    private val viewModel: QuizViewModel by activityViewModels()
    private var currentQuestion: QuestionWithChoices? = null
    private var currentAnswer: Answer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)
        view.btnHome.setOnClickListener {
            viewModel.reset()
            Navigation.findNavController(view).navigate(R.id.homeFragment)
        }
        view.btnNext.setOnClickListener {
            checkAnswer()
            if (viewModel.currentIndex.value!! < viewModel.questions.size - 1) {
                viewModel.goNext()
                Navigation.findNavController(view).navigate(R.id.quizPrevFragment)
            } else {
                Navigation.findNavController(view).navigate(R.id.resultFragment)
            }
        }
        print("entered new quiz!")
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.currentIndex.observe(viewLifecycleOwner, Observer { currentQuestionId ->
            currentQuestion = viewModel.getCurrentQuestion()
            txtQuestionId.text = currentQuestion!!.question.question
            craftAnswers()
        })
    }

    fun craftAnswers() {
        val choiceType = currentQuestion?.choices?.get(0)?.type
        val context = activity?.applicationContext

        val radioButtonGroup = RadioGroup(context)

        when (choiceType) {
            ChoiceType.One.toString() -> {
                for (choice in currentQuestion?.choices!!) {
                    print("ONE")
                    val radioButton = RadioButton(context)
                    radioButton.text = choice.answer
                    radioButton.tag = choice.id
                    radioButtonGroup.addView(radioButton)
                }

                layoutAnswers.addView(radioButtonGroup)
            }
            ChoiceType.Many.toString() -> {
                print("MANY")
                for (choice in currentQuestion?.choices!!) {
                    val checkBox = CheckBox(context)
                    checkBox.text = choice.answer
                    checkBox.tag = choice.id
                    checkBox.isChecked = false

                    layoutAnswers.addView(checkBox)
                }
            }
            ChoiceType.Text.toString() -> {
                val textInput = EditText(context)
                textInput.height = 200
                textInput.tag = currentQuestion?.choices?.get(0)?.answer ?: "ANSWER MUST BE NOT NULL"
                layoutAnswers.addView(textInput)
            }
        }
    }

    fun checkAnswer() {
        val choiceType = currentQuestion?.choices?.get(0)?.type
        val answerViews = layoutAnswers.allViews

        when (choiceType) {
            ChoiceType.One.toString() -> {
                for (answerView in answerViews) {
                    if (answerView is RadioGroup) {
                        val answerRadioGroup = answerView as RadioGroup
                        for (radioButton in answerRadioGroup.children) {
                            if ((radioButton as RadioButton).isChecked) {
                                val choiceId = radioButton.tag.toString()
                                currentAnswer = Answer(
                                    currentQuestion?.question?.id!!,
                                    choiceId,
                                    isCorrectAnswer(choiceId)
                                )
                                break
                            }
                        }
                        break
                    }
                }
            }

            ChoiceType.Many.toString() -> {
                var answerFormatted = "" //1;3;5
                for (answerView in answerViews) {
                    if (answerView is CheckBox) {
                        val answerCheckBox = answerView as CheckBox
                        if (answerView.isChecked) {
                            val choiceId = answerCheckBox.tag.toString()
                            answerFormatted += "$choiceId;";
                        }
                    }
                }

                currentAnswer = Answer(
                    currentQuestion?.question?.id!!,
                    answerFormatted,
                    isCorrectAnswer(answerFormatted)
                )
            }
            ChoiceType.Text.toString() -> {
                for (answerView in answerViews) {
                    if (answerView is EditText) {
                        val answerEditText = answerView as EditText
                        val answerText = answerEditText.tag.toString()
                        if(answerEditText.text.trim().toString().lowercase().equals(answerText.lowercase())) {
                            viewModel.addCorrectScore()
                            currentAnswer = Answer(
                                currentQuestion?.question?.id!!,
                                answerText,
                                true
                            )
                        }
                    }
                }
            }
        }

        if (currentAnswer != null) {
            GlobalScope.launch {
                context?.let {
                    val currentAnswerEntity = com.example.quizapp.database.entities.Answer(
                        currentAnswer!!
                    )
                    QuizDatabase(it).answerDao().addAnswer(currentAnswerEntity)
                }
            }
        }
    }

    fun isCorrectAnswer(selectedChoice: String): Boolean {
        if(selectedChoice.equals("")) return false

        val choiceType = currentQuestion?.choices?.get(0)?.type

        when (choiceType) {
            ChoiceType.One.toString() -> {
                for (choice in currentQuestion?.choices!!) {
                    if (choice.isCorrect && choice.id.equals(selectedChoice.toLong())) {
                        viewModel.addCorrectScore()
                        return true
                    }
                }
                return false
            }
            ChoiceType.Many.toString() -> {
                print("MANY")
                var answers = selectedChoice.split(";")
                var correctCount = 0

//              INPUT = 1;2;
                for(answer in answers) {
                    if(answer != "") {
                        for (choice in currentQuestion?.choices!!) {
                            if(choice.isCorrect && choice.id.equals(answer.toLong())) {
                                correctCount++
                                break
                            }
                        }
                    }
                }

                if(correctCount == answers.size - 1) {
                    viewModel.addCorrectScore()
                    return true
                } else {
                    return false
                }
            }
            ChoiceType.Text.toString() -> {
                print("TEXT")
//                var textInput = EditText(context)
//                textInput.height = 200
//                layoutAnswers.addView(textInput)
            }
        }
        return false
    }
}