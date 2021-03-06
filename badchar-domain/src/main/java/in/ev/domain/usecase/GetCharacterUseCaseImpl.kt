package `in`.ev.domain.usecase

import `in`.ev.domain.model.Character
import `in`.ev.domain.model.Response
import `in`.ev.domain.repository.BadCharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterUseCaseImpl @Inject constructor(private val repository: BadCharacterRepository): GetCharacterUsecase {
    override suspend fun execute(): Flow<Response<List<Character>>> {
       return repository.getBadCharacters()
    }
}