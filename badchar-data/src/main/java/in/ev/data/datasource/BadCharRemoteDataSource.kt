package `in`.ev.data.datasource

import `in`.ev.data.model.CharacterEntity
import `in`.ev.data.model.EntityResultWrapper
import `in`.ev.data.model.ErrorEntity
import `in`.ev.data.network.api.BadCharacterApi
import com.squareup.moshi.JsonAdapter
import retrofit2.Retrofit
import javax.inject.Inject

class BadCharRemoteDataSource @Inject constructor(
    private val badCharacterApi: BadCharacterApi,
    retrofitClient: Retrofit, moshiAdapter: JsonAdapter<ErrorEntity>
) : BaseDataSource(
    retrofitClient,
    moshiAdapter
) {
    suspend fun getBadCharacters(): EntityResultWrapper<List<CharacterEntity>> {
        return getResponse(
            request = { badCharacterApi.getAllBadCharacters() }
        )
    }

}