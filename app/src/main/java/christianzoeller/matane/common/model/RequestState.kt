package christianzoeller.matane.common.model

sealed interface RequestState<out T> {
    data object Loading : RequestState<Nothing>
}

sealed interface RequestResult<out T> : RequestState<T> {
    data class Success<T>(val data: T) : RequestResult<T>
    data class Error(val exception: Throwable) : RequestResult<Nothing>
}