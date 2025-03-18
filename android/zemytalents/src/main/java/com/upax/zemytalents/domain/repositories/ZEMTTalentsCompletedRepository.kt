package com.upax.zemytalents.domain.repositories

interface ZEMTTalentsCompletedRepository {
    suspend fun completeTalents(collaboratorId: String)
}