package com.example.quizapp_frontend.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.quizapp_frontend.api.QuestionResponse
import com.example.quizapp_frontend.database.AppDatabase
import com.example.quizapp_frontend.database.dao.QuestionDao
import com.example.quizapp_frontend.model.QuestionEntity


class QuestionRepository(application: Application) {
    private var questionDao: QuestionDao
    private val database: AppDatabase
    private val DB_NAME = "Database"
    init{
        database = Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME).build()
        questionDao = database.questionDao()
    }
    fun insert(questionsResponse : List<QuestionResponse>){
        val questionsToInsert = convertQuestionResponseToDbObject(questionsResponse)
        InsertAsyncTask(questionDao).execute(questionsToInsert)
    }

    fun convertQuestionResponseToDbObject(questionsToConvert : List<QuestionResponse>) : List<QuestionEntity>{
        val convertedQuestions = mutableListOf<QuestionEntity>()
        for(currentQuestion in questionsToConvert){
            convertedQuestions.add(
                QuestionEntity(
                currentQuestion.id,
                currentQuestion.question,
                currentQuestion.correctAnswer,
                currentQuestion.incorrectAnswers[0],
                currentQuestion.incorrectAnswers[1],
                currentQuestion.incorrectAnswers[2])
            )
        }
        return convertedQuestions
    }

    fun getRandomQuestion() : LiveData<QuestionEntity>{
        return questionDao.getRandomQuestion()
    }
    private class InsertAsyncTask(questionDao: QuestionDao) :
    AsyncTask<List<QuestionEntity?>?, Void?, Void?>() {
        private val questionDao = questionDao

        override fun doInBackground(vararg lists: List<QuestionEntity?>?): Void? {
            questionDao.deleteAll()
            questionDao.insert(lists[0])
            return null
        }
    }
}