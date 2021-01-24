package `in`.ev.data.repository

import `in`.ev.data.datasource.BadCharRemoteDataSource
import `in`.ev.data.mappers.toDomain
import `in`.ev.data.model.CharacterEntity
import `in`.ev.data.model.EntityResultWrapper
import `in`.ev.data.model.EntityResultWrapper.*
import `in`.ev.domain.model.Character
import `in`.ev.domain.model.Response
import `in`.ev.domain.model.Response.*
import `in`.ev.domain.repository.BadCharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BadcharRepositoryImpl @Inject constructor(private val dataSource: BadCharRemoteDataSource) :
    BadCharacterRepository {
    override suspend fun getBadCharacters(): Flow<Response<List<Character>>> {
        return flow {
            emit(Loading(true))
            when (val result: EntityResultWrapper<List<CharacterEntity>> =
                dataSource.getBadCharacters()) {
                is Success -> {
                    val characters = result.data?.map { toDomain(it) }
                    emit(Loading(false))
                    emit(ApiCallSuccess(characters))
                }
                is Error -> {
                    val error = toDomain(result.error)
                    emit(Loading(false))
                    emit(ApiCallError(error))
                }
            }
        }
    }
}