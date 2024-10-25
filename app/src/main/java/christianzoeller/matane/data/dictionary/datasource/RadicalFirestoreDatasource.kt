package christianzoeller.matane.data.dictionary.datasource

import christianzoeller.matane.data.dictionary.model.KanjiWithRadicals
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import javax.inject.Inject

private const val kanjiRadicalsCollectionId = "kanji-radicals"

class RadicalFirestoreDatasource @Inject constructor() {
    private val db = Firebase.firestore

    suspend fun getRadicalsForKanji(kanjiId: Int) =
        db.collection(kanjiRadicalsCollectionId)
            .document(kanjiId.toString())
            .get()
            .data(KanjiWithRadicals.serializer())
            .radicals
}