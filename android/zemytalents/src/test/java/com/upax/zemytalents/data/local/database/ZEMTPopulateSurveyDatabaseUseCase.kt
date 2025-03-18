package com.upax.zemytalents.data.local.database

import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerOptionDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTBreakDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTQuestionDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTSurveyDiscoverEntity
import kotlin.random.Random

internal class ZEMTPopulateSurveyDatabaseUseCase(
    private val database: ZEMTTalentsDatabase,
    private val surveyId: Int = 99,
    private val initialGroupQuestionsIndex: Int = 0,
    private val totalQuestions: Int = 100
) {

    suspend fun populateDatabase() {
        insertDiscoverSurvey()
        insertBreaks()
        insertQuestions()
        insertsAnswers()
    }

    private suspend fun insertDiscoverSurvey() {
        database.surveyDiscoverDao().insertDiscoverSurvey(
            ZEMTSurveyDiscoverEntity(
                surveyId = surveyId,
                groupQuestionIndex = initialGroupQuestionsIndex
            )
        )
    }

    private suspend fun insertBreaks() {
        database.breakDiscoverDao()
            .insertAll(
                listOf(
                    ZEMTBreakDiscoverEntity(
                        surveyId = surveyId,
                        groupQuestionIndex = 50,
                        seen = false,
                        text = ""
                    ),
                    ZEMTBreakDiscoverEntity(
                        surveyId = surveyId,
                        groupQuestionIndex = 100,
                        seen = false,
                        text = ""
                    )
                )
            )
    }
    private suspend fun insertQuestions() {
        val questions = mutableListOf<ZEMTQuestionDiscoverEntity>()
        (1..totalQuestions / 2).forEach { groupQuestionIndex ->
            repeat(2) {
                questions.add(
                    ZEMTQuestionDiscoverEntity(
                        questionId = Random.nextInt(),
                        text = "This is the question $it",
                        surveyId = surveyId,
                        order = it,
                        groupQuestionIndex = groupQuestionIndex,
                        lastSeen = 0
                    )
                )
            }
        }
        database.questionDiscoverDao().insertAll(questions)
    }

    private suspend fun insertsAnswers() {
        val answersDao = database.answerOptionDiscoverDao()
        var answerId = 1
        (0..199).map {
            val answers = listOf(
                ZEMTAnswerOptionDiscoverEntity(
                    answerOptionId = answerId++,
                    questionId = it,
                    order = 1,
                    text = "Me describe completamente",
                    value = 5
                ),
                ZEMTAnswerOptionDiscoverEntity(
                    answerOptionId = answerId++,
                    questionId = it,
                    order = 2,
                    text = "A veces me describe",
                    value = 3
                ),
                ZEMTAnswerOptionDiscoverEntity(
                    answerOptionId = answerId++,
                    questionId = it,
                    order = 3,
                    text = "Ninguna me describe / Ambas me describen",
                    value = 0
                )
            ).shuffled()
            answersDao.insertAll(answers)
        }
    }

}