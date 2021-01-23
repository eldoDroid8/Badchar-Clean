package `in`.ev.data.datasource

import `in`.ev.data.model.EntityResultWrapper
import `in`.ev.data.model.ErrorEntity
import com.squareup.moshi.JsonAdapter
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.net.SocketException

abstract class BaseDataSource constructor(val retrofit: Retrofit, val moshiErrorAdapter:
JsonAdapter<ErrorEntity>) {
    suspend fun <T : Any> getResponse (request: suspend () -> Response<T>, defaultErrorMessage:
    String): EntityResultWrapper<T> {
        return try {
            val result = request.invoke()
            return if (result.isSuccessful) {
                if (null == result.body()) {
                    EntityResultWrapper.Error(ErrorEntity(status_message = "Empty response"))
                } else {
                    //ApiCallSuccess(result.body())
                    EntityResultWrapper.Success(result.body())
                }
            } else {
              val errorResponse = parseError(result)
                val apiCallError = errorResponse
                    ?: ErrorEntity(status_message = defaultErrorMessage)
                EntityResultWrapper.Error(apiCallError)
            }
        } catch (e: IOException) {
            EntityResultWrapper.Error( ErrorEntity(status_message =  "Unknown error"))
        }
        catch (e: SocketException){
            EntityResultWrapper.Error( ErrorEntity(status_message =  "Please check your network connection"))
        }
        catch(e: HttpException) {
            val errorResponse = convertErrorBody(e)
            EntityResultWrapper.Error(errorResponse)
        }
    }

  fun parseError(response: Response<*>): ErrorEntity {
        val converter = retrofit.responseBodyConverter<ErrorEntity>(ErrorEntity::class.java, arrayOfNulls
            (0))
        return try {
            converter.convert(response?.errorBody()) ?: ErrorEntity(status_message = "Unknown " +
                    "error")

        } catch (e: IOException) {
            ErrorEntity()
        }
    }

    private fun convertErrorBody(throwable: HttpException): ErrorEntity {
        return try {
            val response =  moshiErrorAdapter.fromJson(throwable?.response()?.errorBody()?.source())
            response ?: ErrorEntity(status_message = "Unknown Error")
        } catch (exception: Exception) {
           ErrorEntity(status_message = "Unknown error")
        }
    }

}