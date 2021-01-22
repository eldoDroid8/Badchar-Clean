package `in`.ev.data.repository

import `in`.ev.data.datasource.BadCharRemoteDataSource
import `in`.ev.domain.model.Character
import `in`.ev.domain.repository.BadCharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BadcharRepositoryImpl @Inject constructor(val dataSource: BadCharRemoteDataSource):
    BadCharacterRepository {
    override suspend fun getBadCharacters(): Flow<List<Character>> {
       return flow {

       }
    }
}