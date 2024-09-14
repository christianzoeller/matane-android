package christianzoeller.matane.common.extensions

import christianzoeller.matane.common.model.RequestResult
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun <TResult, R> Task<TResult>.handleFirestoreQueryResult(
    successDataTransform: (TResult) -> R
) = suspendCoroutine { continuation ->
    this
        .addOnSuccessListener { result ->
            try {
                val data = successDataTransform(result)
                continuation.resume(RequestResult.Success(data))
            } catch (e: Exception) {
                Firebase.crashlytics.recordException(e)
                continuation.resume(RequestResult.Error(e))
            }
        }
        .addOnFailureListener { exception ->
            Firebase.crashlytics.recordException(exception)
            continuation.resume(RequestResult.Error(exception))
        }
        // TODO add on cancelled listener
}