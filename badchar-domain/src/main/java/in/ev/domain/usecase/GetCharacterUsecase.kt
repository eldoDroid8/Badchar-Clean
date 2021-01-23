package `in`.ev.domain.usecase

import `in`.ev.domain.model.Character
import `in`.ev.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface GetCharacterUsecase {
    suspend fun execute(): Flow<Response<List<Character>>>
}