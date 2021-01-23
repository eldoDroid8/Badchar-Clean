package `in`.ev.data.datasource

import `in`.ev.data.model.CharacterEntity
import `in`.ev.data.model.EntityResultWrapper
import `in`.ev.data.model.ErrorEntity
import `in`.ev.data.network.api.BadCharacterApi
import com.squareup.moshi.JsonAdapter
import retrofit2.Retrofit
import javax.inject.Inject

class BadCharRemoteDataSource @Inject constructor(val badCharacterApi: BadCharacterApi, val
retrofitClient: Retrofit, moshiAdapter: JsonAdapter<ErrorEntity>): BaseDataSource(retrofitClient,
    moshiAdapter) {

   suspend fun getBadCharacters(): EntityResultWrapper<List<CharacterEntity>> {
        val response  = getResponse(request = {badCharacterApi.getAllBadCharacters()},
            defaultErrorMessage = "Please check your network connection")
        return response
    }

}