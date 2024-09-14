package christianzoeller.matane.data.dictionary.datasource

import christianzoeller.matane.common.extensions.fetchPaginated
import christianzoeller.matane.common.extensions.handleFirestoreQueryResult
import christianzoeller.matane.common.model.RequestResult
import christianzoeller.matane.data.dictionary.model.KanjiInContext
import christianzoeller.matane.data.dictionary.model.firestore.KanjiFirestoreDto
import christianzoeller.matane.data.dictionary.model.firestore.KanjiInContextFirestoreDto
import christianzoeller.matane.data.dictionary.model.firestore.toKanji
import christianzoeller.matane.data.dictionary.model.firestore.toKanjiInContext
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.firestore
import javax.inject.Inject

private const val kanjiCollectionId = "kanji"
private const val kanjiByGradeCollectionId = "kanji-by-grade"
private const val kanjiByFrequencyCollectionId = "kanji-by-frequency"

class KanjiFirestoreDatasource @Inject constructor() {
    private val db = Firebase.firestore

    suspend fun getKanjiById(id: Int) =
        db.collection(kanjiCollectionId)
            .document(id.toString())
            .get()
            .handleFirestoreQueryResult { result ->
                val firestoreKanji = result.toObject(KanjiFirestoreDto::class.java)
                firestoreKanji?.toKanji() // TODO should we allow null here?
            }

    suspend fun getKanjiByGrade(
        lastKanji: KanjiInContext?,
        numberOfItems: Int
    ): RequestResult<List<KanjiInContext>> {
        val gradeField = FieldPath.of("Priority")
        val idField = FieldPath.of("Id")

        val collectionOrdered = db
            .collection(kanjiByGradeCollectionId)
            .orderBy(gradeField)
            .orderBy(idField)

        val collectionWithCorrectStart = if (lastKanji != null) {
            collectionOrdered.startAfter(lastKanji.priority, lastKanji.id)
        } else {
            collectionOrdered
        }

        return collectionWithCorrectStart
            .limit(numberOfItems.toLong())
            .get()
            .handleFirestoreQueryResult { result ->
                result.documents.mapNotNull { document ->
                    document
                        .toObject(KanjiInContextFirestoreDto::class.java)
                        ?.toKanjiInContext(requirePriority = true)
                }
            }
    }

    suspend fun getMostFrequentKanji(
        currentOffset: Int,
        numberOfItems: Int
    ): RequestResult<List<KanjiInContext>> {
        val frequencyStartValue = currentOffset.toString().padStart(4)
        return db.collection(kanjiByFrequencyCollectionId)
            .fetchPaginated(
                orderBy = "Priority",
                startAfter = frequencyStartValue,
                limit = numberOfItems
            )
            .handleFirestoreQueryResult { result ->
                result.documents.mapNotNull { document ->
                    document
                        .toObject(KanjiInContextFirestoreDto::class.java)
                        ?.toKanjiInContext(requirePriority = true)
                }
            }
    }
}