package com.upax.zemytalents.data.remote

import com.upax.zemytalents.data.remote.requests.ZEMTSyncSurveyAnswersRequestBuilder
import com.upax.zemytalents.data.repository.fake.ZEMTFakeDeviceRepository
import com.upax.zemytalents.domain.models.modules.discover.ZEMTAnswerSavedMother
import org.junit.Assert.assertEquals
import org.junit.Test

class ZEMTSyncSurveyAnswersRequestBuilderTest {

    private val collaboratorId = ""

    @Test
    fun `request should have initial latitude from answer with the lowest groupQuestionIndex`() {
        val firstAnswer = ZEMTAnswerSavedMother.apply(groupQuestionIndex = 1)
        val answers = listOf(
            firstAnswer,
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 2),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 3),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 4),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 5),
        ).shuffled()
        val builder = ZEMTSyncSurveyAnswersRequestBuilder(ZEMTFakeDeviceRepository())

        val request = builder.build(collaboratorId, answers)

        assertEquals(firstAnswer.location.latitude, request.initialLatitude)
    }

    @Test
    fun `request should have initial longitude from answer with the lowest groupQuestionIndex`() {
        val firstAnswer = ZEMTAnswerSavedMother.apply(groupQuestionIndex = 1)
        val answers = listOf(
            firstAnswer,
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 2),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 3),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 4),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 5),
        ).shuffled()
        val builder = ZEMTSyncSurveyAnswersRequestBuilder(ZEMTFakeDeviceRepository())

        val request = builder.build(collaboratorId, answers)

        assertEquals(firstAnswer.location.longitude, request.initialLongitude)
    }

    @Test
    fun `request should have end latitude from answer with the highest groupQuestionIndex`() {
        val lastAnswer = ZEMTAnswerSavedMother.apply(groupQuestionIndex = 5)
        val answers = listOf(
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 1),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 2),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 3),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 4),
            lastAnswer
        ).shuffled()
        val builder = ZEMTSyncSurveyAnswersRequestBuilder(ZEMTFakeDeviceRepository())

        val request = builder.build(collaboratorId, answers)

        assertEquals(lastAnswer.location.latitude, request.endLatitude)
    }

    @Test
    fun `request should have end longitude from answer with the highest groupQuestionIndex`() {
        val lastAnswer = ZEMTAnswerSavedMother.apply(groupQuestionIndex = 5)
        val answers = listOf(
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 1),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 2),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 3),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 4),
            lastAnswer
        ).shuffled()
        val builder = ZEMTSyncSurveyAnswersRequestBuilder(ZEMTFakeDeviceRepository())

        val request = builder.build(collaboratorId, answers)

        assertEquals(lastAnswer.location.longitude, request.endLongitude)
    }

    @Test
    fun `request should have initial date from answer with the lowest groupQuestionIndex`() {
        val firstAnswer = ZEMTAnswerSavedMother.apply(groupQuestionIndex = 1)
        val answers = listOf(
            firstAnswer,
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 2),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 3),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 4),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 5),
        ).shuffled()
        val builder = ZEMTSyncSurveyAnswersRequestBuilder(ZEMTFakeDeviceRepository())

        val request = builder.build(collaboratorId, answers)

        assertEquals(firstAnswer.date, request.initialDate)
    }

    @Test
    fun `request should have end date from answer with the highest groupQuestionIndex`() {
        val lastAnswer = ZEMTAnswerSavedMother.apply(groupQuestionIndex = 5)
        val answers = listOf(
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 1),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 2),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 3),
            ZEMTAnswerSavedMother.apply(groupQuestionIndex = 4),
            lastAnswer
        ).shuffled()
        val builder = ZEMTSyncSurveyAnswersRequestBuilder(ZEMTFakeDeviceRepository())

        val request = builder.build(collaboratorId, answers)

        assertEquals(lastAnswer.date, request.endDate)
    }

    @Test
    fun `request should have collaborator id from parameter`() {
        val answers = listOf(ZEMTAnswerSavedMother.random())
        val builder = ZEMTSyncSurveyAnswersRequestBuilder(ZEMTFakeDeviceRepository())

        val request = builder.build(collaboratorId, answers)

        assertEquals(collaboratorId, request.collaboratorId)
    }

    @Test
    fun `request should have device name from repository`() {
        val answers = listOf(ZEMTAnswerSavedMother.random())
        val deviceName = "Samsung Galaxy"
        val builder = ZEMTSyncSurveyAnswersRequestBuilder(
            ZEMTFakeDeviceRepository(name = deviceName)
        )

        val request = builder.build(collaboratorId, answers)

        assertEquals(deviceName, request.device)
    }

    @Test
    fun `request should have platform from repository`() {
        val answers = listOf(ZEMTAnswerSavedMother.random())
        val platform = "Android"
        val builder = ZEMTSyncSurveyAnswersRequestBuilder(
            ZEMTFakeDeviceRepository(platform = platform)
        )

        val request = builder.build(collaboratorId, answers)

        assertEquals(platform, request.platform)
    }


}