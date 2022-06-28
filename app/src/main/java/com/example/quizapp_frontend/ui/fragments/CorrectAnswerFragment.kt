package com.example.quizapp_frontend.ui.fragments

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp_frontend.databinding.FragmentCorrectAnswerBinding
import com.example.quizapp_frontend.viewmodel.QuestionViewModel

class CorrectAnswerFragment : Fragment() {
    private lateinit var questionViewModel : QuestionViewModel
    private lateinit var binding: FragmentCorrectAnswerBinding

    override fun onStart() {
        super.onStart()
        questionViewModel = ViewModelProvider(requireActivity())[QuestionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val answer = arguments?.getString("answer")
        binding = FragmentCorrectAnswerBinding.inflate(inflater, container, false)
        binding.correctAnswerTextViewId.setText(answer)
        binding.cardViewCorrectAnswer.setOnClickListener { // adding the color to be shown
            onClickCardView(it)
        }
        return binding.root
    }

    private fun onClickCardView(view: View) {
        if(questionViewModel.answerSelected.value == false) {
            val objectAnimator = ObjectAnimator.ofInt(
                binding.cardViewCorrectAnswer,
                "backgroundColor",
                Color.GREEN,
                Color.GRAY
            )
            objectAnimator.duration = 1000
            objectAnimator.setEvaluator(ArgbEvaluator())
            objectAnimator.repeatCount = Animation.REVERSE
            objectAnimator.repeatCount = Animation.INFINITE
            objectAnimator.start()
            questionViewModel.answerSelected()
            questionViewModel.updateCurrentQuestion()
        }
    }


}