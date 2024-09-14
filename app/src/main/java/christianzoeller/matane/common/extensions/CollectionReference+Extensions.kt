package christianzoeller.matane.common.extensions

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.QuerySnapshot

/**
 * Fetches documents from this collection in a paginated manner.
 *
 * @param orderBy The name of the field which is used to order the data.
 * @param startAfter The value of the field specified by [orderBy] after which
 * query results should start.
 * @param limit The desired number of items.
 */
fun CollectionReference.fetchPaginated(
    orderBy: String,
    startAfter: Any,
    limit: Int
): Task<QuerySnapshot> {
    val field = FieldPath.of(orderBy)
    return this
        .orderBy(field)
        .startAfter(startAfter)
        .limit(limit.toLong())
        .get()
}
