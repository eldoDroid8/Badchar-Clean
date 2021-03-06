package `in`.ev.domain.repository

import `in`.ev.domain.model.Character
import `in`.ev.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface BadCharacterRepository {
    suspend fun getBadCharacters(): Flow<Response<List<Character>>>
}