package com.example.quizapp_frontend.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp_frontend.R
import com.example.quizapp_frontend.navigation.NavigationMessage
import com.example.quizapp_frontend.viewmodel.GameStatisticsViewModel
import com.example.quizapp_frontend.viewmodel.NavigationViewModel

class GameFinishedDialogFragment : DialogFragment() {
    private lateinit var navigationViewModel:NavigationViewModel
    private lateinit var gameStatisticsViewModel :GameStatisticsViewModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        navigationViewModel = ViewModelProvider(requireActivity())[NavigationViewModel::class.java]
        gameStatisticsViewModel = ViewModelProvider(requireActivity())[GameStatisticsViewModel::class.java]
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Number of questions: ${gameStatisticsViewModel.gameStatistics.totalNumberOfQuestions}\n" +
                "Correct Answers: ${gameStatisticsViewModel.gameStatistics.numberOfCorrectAnswers}\n"+
                "Incorrect Answers: ${gameStatisticsViewModel.gameStatistics.numberOfIncorrectAnswers}\n" +
                "Passed Time: ${gameStatisticsViewModel.gameStatistics.passedTimeInSeconds} s").setTitle("Game finished")

        builder.setPositiveButton("OK") { dialog, which ->
            navigationViewModel.updateNavigationValue(NavigationMessage.MAIN_MENU)
        }

        return builder.create()
    }
    companion object {
        const val TAG = "GameFinishedDialogFragment"
    }

}