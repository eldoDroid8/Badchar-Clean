package `in`.ev.data.network.api

import `in`.ev.data.model.CharacterEntity
import retrofit2.Response
import retrofit2.http.GET

interface BadCharacterApi {
    @GET("characters")
    suspend fun getAllBadCharacters(): Response<List<CharacterEntity>>

}