package `in`.ev.data.repository

import `in`.ev.data.datasource.BadCharRemoteDataSource
import `in`.ev.data.mappers.toDomain
import `in`.ev.data.model.EntityResultWrapper
import `in`.ev.domain.model.Character
import `in`.ev.domain.model.Response
import `in`.ev.domain.repository.BadCharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BadcharRepositoryImpl @Inject constructor(val dataSource: BadCharRemoteDataSource)
    : BadCharacterRepository {
    override suspend fun getBadCharacters(): Flow<Response<List<Character>>> {
    return flow<Response<List<Character>>> {
        emit(Response.Loading(true))
        when (val result = dataSource.getBadCharacters()) {
            is EntityResultWrapper.Success -> {
                val characters = result?.data?.map {
                    it.toDomain(it)
                }
                emit(Response.Loading(false))
                emit(Response.ApiCallSuccess(characters))
            }
            is EntityResultWrapper.Error -> {
                val error = result.error.toDomain(result.error)
                emit(Response.Loading(false))
                emit(Response.ApiCallError(error))
            }
        }
    }
}
}