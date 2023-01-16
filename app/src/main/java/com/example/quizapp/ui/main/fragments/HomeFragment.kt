package com.example.quizapp.ui.main.fragments


import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.quizapp.R
import com.example.quizapp.classes.ChoiceType
import com.example.quizapp.database.QuizDatabase
import com.example.quizapp.database.entities.Choice
import com.example.quizapp.database.entities.Question
import com.example.quizapp.models.QuizViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: QuizViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.btnStartQuiz.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.quizPrevFragment)
        }

        populateDatabase()

        return view
    }

    fun populateDatabase() {
        GlobalScope.launch {
            context?.let {val db = QuizDatabase(it)
                db.questionDao().deleteAll()
                db.choiceDao().deleteAll()
                db.answerDao().deleteAll()
                db.quizDao().deleteAll()

                val question1 = Question("Kotlin question 1")
                var insertedQuestionId = db.questionDao().addQuestion(question1)

                val choice1 = Choice(0, insertedQuestionId, "Answer 1", false, ChoiceType.One.toString())
                val choice2 = Choice(0, insertedQuestionId, "Answer 2", true, ChoiceType.One.toString())
                val choice3 = Choice(0, insertedQuestionId, "Answer 3", false, ChoiceType.One.toString())
                val choice4 = Choice(0, insertedQuestionId, "Answer 4", false, ChoiceType.One.toString())

                db.choiceDao().addChoice(choice1)
                db.choiceDao().addChoice(choice2)
                db.choiceDao().addChoice(choice3)
                db.choiceDao().addChoice(choice4)

                val question2 = Question("Kotlin question 2")
                insertedQuestionId = db.questionDao().addQuestion(question2)

                val choice21 = Choice(0, insertedQuestionId, "Answer 2-1", true, ChoiceType.Many.toString())
                val choice22 = Choice(0, insertedQuestionId, "Answer 2-2", false, ChoiceType.Many.toString())
                val choice23 = Choice(0, insertedQuestionId, "Answer 2-3", false, ChoiceType.Many.toString())
                val choice24 = Choice(0, insertedQuestionId, "Answer 2-4", true, ChoiceType.Many.toString())

                db.choiceDao().addChoice(choice21)
                db.choiceDao().addChoice(choice22)
                db.choiceDao().addChoice(choice23)
                db.choiceDao().addChoice(choice24)

                val question3 = Question("Kotlin question 3")
                insertedQuestionId = db.questionDao().addQuestion(question3)

                val choice31 = Choice(0, insertedQuestionId, "Answer 3-1", true, ChoiceType.Text.toString())

                db.choiceDao().addChoice(choice31)

                print(db.choiceDao().getAllChoices())

                viewModel.questions = db.questionDao().getQuestionWithChoices()
            }
        }
    }

    fun startQuiz(view: View) {
//        Nav
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}