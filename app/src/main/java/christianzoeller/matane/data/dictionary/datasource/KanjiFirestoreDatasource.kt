package christianzoeller.matane.data.dictionary.datasource

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

    // TODO implement paging
    suspend fun getKanjiByGrade(): RequestResult<List<KanjiInContext>> {
        val gradeField = FieldPath.of("Priority")
        return db.collection(kanjiByGradeCollectionId)
            .orderBy(gradeField)
            .limit(30) // TODO turn into parameter
            .get()
            .handleFirestoreQueryResult { result ->
                result.documents.mapNotNull { document ->
                    document
                        .toObject(KanjiInContextFirestoreDto::class.java)
                        ?.toKanjiInContext(requirePriority = true)
                }
            }
    }

    suspend fun getMostFrequentKanji(): RequestResult<List<KanjiInContext>> {
        val frequencyField = FieldPath.of("Priority")
        return db.collection(kanjiByFrequencyCollectionId)
            .orderBy(frequencyField)
            .limit(30) // TODO turn into parameter
            .get()
            .handleFirestoreQueryResult { result ->
                result.documents.mapNotNull { document ->
                    document
                        .toObject(KanjiInContextFirestoreDto::class.java)
                        ?.toKanjiInContext(requirePriority = true)
                }
            }
    }
}