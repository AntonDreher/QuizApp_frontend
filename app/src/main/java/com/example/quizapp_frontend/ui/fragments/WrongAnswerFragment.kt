package com.example.quizapp_frontend.ui.fragments

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp_frontend.databinding.FragmentWrongAnswerBinding
import com.example.quizapp_frontend.viewmodel.GameStatisticsViewModel
import com.example.quizapp_frontend.viewmodel.QuestionViewModel

class WrongAnswerFragment : Fragment() {
    private lateinit var binding: FragmentWrongAnswerBinding
    private lateinit var questionViewModel : QuestionViewModel
    private lateinit var gameStatisticsViewModel: GameStatisticsViewModel

    override fun onStart() {
        super.onStart()
        questionViewModel = ViewModelProvider(requireActivity())[QuestionViewModel::class.java]
        gameStatisticsViewModel = ViewModelProvider(requireActivity())[GameStatisticsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val answer = arguments?.getString("answer")
        binding = FragmentWrongAnswerBinding.inflate(inflater, container, false)
        binding.wrongAnswerTextViewId.setText(answer)
        binding.cardViewWrongAnswer.setOnClickListener { // adding the color to be shown
            onClickCardView(it)
        }
        binding.cardViewWrongAnswer.clearAnimation()
        return binding.root
    }

    private fun onClickCardView(view: View) {
        if(questionViewModel.answerSelected.value == false) {
            gameStatisticsViewModel.wrongAnswerSelected()
            val objectAnimator = ObjectAnimator.ofInt(
                binding.cardViewWrongAnswer,
                "backgroundColor",
                Color.RED,
                Color.GRAY
            )
            objectAnimator.duration = 1000
            objectAnimator.setEvaluator(ArgbEvaluator())
            objectAnimator.repeatCount = Animation.REVERSE
            objectAnimator.repeatCount = Animation.INFINITE
            objectAnimator.start()
            questionViewModel.answerSelected()
            Handler(Looper.getMainLooper()).postDelayed({
                questionViewModel.updateCurrentQuestion()
            }, 3000)
        }
    }

}