package christianzoeller.matane.common.extensions

import dev.gitlive.firebase.firestore.CollectionReference
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FieldPath

/**
 * Fetches documents from this collection in a paginated manner and transforms
 * them to a desired result type.
 *
 * @param T The type the documents in the collection should be transformed to.
 *
 * @param orderBy The name of the field which is used to order the data.
 * @param startAfter The value of the field specified by [orderBy] after which
 * query results should start.
 * @param limit The desired number of items.
 * @param transform A function to map from [DocumentSnapshot] to [T].
 */
suspend fun <T> CollectionReference.fetchPaginated(
    orderBy: String,
    startAfter: Any,
    limit: Int,
    transform: (DocumentSnapshot) -> T
): List<T> {
    val field = FieldPath(orderBy)
    return this
        .orderBy(field)
        .startAfter(startAfter)
        .limit(limit.toLong())
        .get()
        .documents
        .map(transform)
}
