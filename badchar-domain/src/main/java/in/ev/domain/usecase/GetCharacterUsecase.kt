package `in`.ev.domain.usecase

import `in`.ev.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface GetCharacterUsecase {
    suspend fun execute(): Flow<List<Character>>
}