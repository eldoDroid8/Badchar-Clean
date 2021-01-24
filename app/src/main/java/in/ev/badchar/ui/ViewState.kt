package `in`.ev.badchar.ui

import `in`.ev.domain.model.Error

sealed class ViewState<out T: Any> {

    data class Loading(val isLoading: Boolean) : ViewState<Nothing>()
    data class Success<out T : Any>(val output: T) : ViewState<T>()
    data class Failure(val throwable: Error) : ViewState<Nothing>()
}



