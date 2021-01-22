package `in`.ev.data.datasource

import `in`.ev.data.network.api.BadCharacterApi
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.net.SocketException
import javax.inject.Inject

class BadCharRemoteDataSource @Inject constructor(val badCharacterApi: BadCharacterApi, val
retrofit: Retrofit): BaseDataSource() {

   suspend fun getBadCharacters() {
        return getResponse(request = {badCharacterApi.getAllBadCharacters()},
                            defaultErrorMessage = "Please check your network connection")
    }
}