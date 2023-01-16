package com.example.quizapp.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.quizapp.R
import com.example.quizapp.models.QuizViewModel
import com.example.quizapp.models.ResultDetailedViewModel
import kotlinx.android.synthetic.main.fragment_result.view.*
import kotlinx.android.synthetic.main.fragment_result_detailed.view.*


class ResultDetailedFragment : Fragment() {

    companion object {
        fun newInstance() = ResultDetailedFragment()
    }

    private lateinit var viewModel: ResultDetailedViewModel
    private val quizViewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result_detailed, container, false)
        view.btnHomeFromAnalysis.setOnClickListener {
            quizViewModel.reset()
            Navigation.findNavController(view).navigate(R.id.homeFragment)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ResultDetailedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}