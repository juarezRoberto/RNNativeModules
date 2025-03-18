package com.upax.zemytalents.data.repository

import com.upax.zemytalents.data.local.database.dao.ZEMTAnswerOptionDiscoverDao
import com.upax.zemytalents.data.local.database.dao.ZEMTBreakDiscoverDao
import com.upax.zemytalents.data.local.database.dao.ZEMTQuestionDiscoverDao
import com.upax.zemytalents.data.local.database.dao.ZEMTSurveyDiscoverDao
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTAnswerOptionDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTBreakDiscoverEntity
import com.upax.zemytalents.data.local.database.entity.discover.ZEMTQuestionDiscoverEntity
import com.upax.zemytalents.data.local.database.mapper.ZEMTAnswerDiscoverToEntityMapper
import com.upax.zemytalents.data.local.database.mapper.ZEMTQuestionDiscoverToEntityMapper
import com.upax.zemytalents.domain.models.modules.discover.ZEMTSurveyDiscover

internal class ZEMTSurveyDiscoverRegistrar(
    private val surveyDao: ZEMTSurveyDiscoverDao,
    private val questionDao: ZEMTQuestionDiscoverDao,
    private val answerOptionDao: ZEMTAnswerOptionDiscoverDao,
    private val breaksDao: ZEMTBreakDiscoverDao,
    private val questionToEntityMapper: ZEMTQuestionDiscoverToEntityMapper,
    private val answerToEntityMapper: ZEMTAnswerDiscoverToEntityMapper
) {

    suspend operator fun invoke(survey: ZEMTSurveyDiscover) {

        surveyDao.updateCurrentGroupQuestionIndex(index = 1)

        val questions = mutableListOf<ZEMTQuestionDiscoverEntity>()
        val answers = mutableListOf<ZEMTAnswerOptionDiscoverEntity>()

        survey.groupQuestions.map { groupQuestion ->
            questions.add(
                questionToEntityMapper.invoke(
                    question = groupQuestion.leftQuestion,
                    surveyId = survey.id,
                    groupQuestionIndex = groupQuestion.index
                )
            )
            questions.add(
                questionToEntityMapper.invoke(
                    question = groupQuestion.rightQuestion,
                    surveyId = survey.id,
                    groupQuestionIndex = groupQuestion.index
                )
            )
            answers.addAll(groupQuestion.leftQuestion.answers.map(answerToEntityMapper))
            answers.addAll(groupQuestion.rightQuestion.answers.map(answerToEntityMapper))
        }

        questionDao.insertAll(questions)
        answerOptionDao.insertAll(answers)

        val breaks = survey.breaks.map {
            ZEMTBreakDiscoverEntity(
                groupQuestionIndex = it.indexGroupQuestion.value,
                text = it.text,
                surveyId = survey.id.value.toInt(),
                seen = false
            )
        }
        breaksDao.insertAll(breaks)
    }

}