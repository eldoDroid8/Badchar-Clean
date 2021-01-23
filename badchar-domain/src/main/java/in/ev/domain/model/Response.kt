package `in`.ev.domain.model


sealed  class Response<out T> {
    data class Loading<T>(val loading: Boolean): Response<T>()
    data class ApiCallSuccess<out T : Any>(val data: T?): Response<T>()
    data class ApiCallError(val error: Error): Response<Nothing>()
}

