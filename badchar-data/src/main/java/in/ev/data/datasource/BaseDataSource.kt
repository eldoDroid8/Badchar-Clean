package `in`.ev.data.datasource

import retrofit2.Response
import java.io.IOException
import java.net.SocketException

abstract class BaseDataSource {
    suspend fun <T: Any> getResponse (request: suspend () -> Response<T>, defaultErrorMessage: String) {
        return try {
            val result = request.invoke()
            return if (result.isSuccessful) {
                if (null == result.body()) {
                    //ApiCallError (ErrorResponse(status_message = "Empty response"))
                } else {
                    //ApiCallSuccess(result.body())
                }
            } else {
              /*  val errorResponse = parseError(result)
                val apiCallError = ApiCallError(errorResponse
                    ?: ErrorResponse(status_message = defaultErrorMessage))
                apiCallError*/
            }
        } catch (e: IOException) {
            //ApiCallError(ErrorResponse(status_message = "Unknown error"))
        }
        catch (e: SocketException){
           // ApiCallError(ErrorResponse(status_message = "Please check your network connection"))
        }
    }


  /*  fun parseError(response: Response<*>): ErrorResponse? {
        //val converter = retrofit.responseBodyConverter<ErrorResponse>(ErrorResponse::class.java, arrayOfNulls(0))
        return try {
            //converter.convert(response.errorBody()!!)
        } catch (e: IOException) {
            //ErrorResponse()
        }
    }*/
}