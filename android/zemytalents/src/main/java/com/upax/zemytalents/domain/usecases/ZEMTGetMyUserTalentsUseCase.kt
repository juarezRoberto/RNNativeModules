package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.models.ZEMTResult
import com.upax.zemytalents.domain.models.ZEMTTalents
import com.upax.zemytalents.domain.models.error.ZEMTDataError
import com.upax.zemytalents.domain.repositories.ZEMTTalentsRepository
import com.upax.zemytalents.domain.repositories.ZEMTUserRepository

internal class ZEMTGetMyUserTalentsUseCase(
    private val userRepository: ZEMTUserRepository,
    private val surveyTalentsRepository: ZEMTTalentsRepository,
) {

    suspend operator fun invoke(): ZEMTResult<ZEMTTalents, ZEMTDataError> {
        return surveyTalentsRepository.getTalents(userRepository.collaboratorId)
    }

}