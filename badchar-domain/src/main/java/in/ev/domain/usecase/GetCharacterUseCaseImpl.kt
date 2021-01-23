package `in`.ev.domain.usecase

import `in`.ev.domain.model.Character
import `in`.ev.domain.model.Response
import `in`.ev.domain.repository.BadCharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterUseCaseImpl @Inject constructor(val repository: BadCharacterRepository): GetCharacterUsecase {
    override suspend fun execute(): Flow<Response<List<Character>>> {
       // return repository.getBadCharacters()
        // TODO: 23/1/21
        return flow {

        }
    }
}