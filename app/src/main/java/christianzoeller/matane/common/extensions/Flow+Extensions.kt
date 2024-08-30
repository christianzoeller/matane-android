package christianzoeller.matane.common.extensions

import christianzoeller.matane.common.model.RequestResult
import christianzoeller.matane.common.model.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun <T, R> combineRequestFlows(
    firstFlow: Flow<RequestState<T>>,
    secondFlow: Flow<RequestState<R>>
): Flow<RequestState<Pair<T, R>>> = combine(firstFlow, secondFlow) { first, second ->
    when {
        first is RequestResult.Error -> RequestResult.Error(first.exception)
        second is RequestResult.Error -> RequestResult.Error(second.exception)

        first is RequestResult.Success && second is RequestResult.Success ->
            RequestResult.Success(Pair(first.data, second.data))

        else -> RequestState.Loading
    }
}