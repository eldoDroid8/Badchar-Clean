package `in`.ev.data.model


sealed  class EntityResultWrapper<out T : Any> {
    data class Success<out T : Any>(val data: T?): EntityResultWrapper<T>()
    data class Error(val error: ErrorEntity): EntityResultWrapper<Nothing>()
}

