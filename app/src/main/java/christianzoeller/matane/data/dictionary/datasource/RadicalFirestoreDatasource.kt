package christianzoeller.matane.data.dictionary.datasource

import christianzoeller.matane.common.extensions.handleFirestoreQueryResult
import christianzoeller.matane.data.dictionary.model.firestore.KanjiWithRadicalsFirestoreDto
import christianzoeller.matane.data.dictionary.model.firestore.toRadicalsInKanji
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import javax.inject.Inject

private const val kanjiRadicalsCollectionId = "kanji-radicals"

class RadicalFirestoreDatasource @Inject constructor() {
    private val db = Firebase.firestore

    suspend fun getRadicalsForKanji(kanjiId: Int) =
        db.collection(kanjiRadicalsCollectionId)
            .document(kanjiId.toString())
            .get()
            .handleFirestoreQueryResult { result ->
                val kanjiWithRadicals = result.toObject(KanjiWithRadicalsFirestoreDto::class.java)
                kanjiWithRadicals?.toRadicalsInKanji()
            }
}