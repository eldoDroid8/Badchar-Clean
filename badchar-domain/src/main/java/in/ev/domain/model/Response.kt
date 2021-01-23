package `in`.ev.domain.model


sealed  class Response<out T : Any> {
    data class Loading(val loading: Boolean): Response<Boolean>()
    data class ApiCallSuccess<out T : Any>(val data: T?): Response<T>()
    data class ApiCallError(val error: Error): Response<Nothing>()
}

