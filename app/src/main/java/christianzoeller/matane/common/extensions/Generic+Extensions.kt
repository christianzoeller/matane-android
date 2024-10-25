package christianzoeller.matane.common.extensions

import christianzoeller.matane.common.model.RequestResult

/**
 * Performs a given request and returns a [RequestResult] based on the outcome.
 *
 * @param T The type of this.
 * @param R The type of the type parameter of [RequestResult] in case the
 * request was successful.
 *
 * @param request The request to perform.
 */
suspend fun <T, R> T.runRequest(
    request: suspend () -> R
): RequestResult<R> {
    val result = runCatching { request() }
    return result.fold(
        onSuccess = { RequestResult.Success(it) },
        onFailure = { RequestResult.Error(it) }
    )
}