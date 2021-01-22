package `in`.ev.domain.repository

import `in`.ev.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface BadCharacterRepository {
    suspend fun getBadCharacters(): Flow<List<Character>>
}