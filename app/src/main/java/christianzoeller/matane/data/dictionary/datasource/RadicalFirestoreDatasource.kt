package christianzoeller.matane.data.dictionary.datasource

import christianzoeller.matane.common.extensions.fetchPaginated
import christianzoeller.matane.data.dictionary.model.KanjiWithRadicals
import christianzoeller.matane.data.dictionary.model.Radical
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import javax.inject.Inject

private const val radicalsCollectionId = "radicals"
private const val kanjiRadicalsCollectionId = "kanji-radicals"

class RadicalFirestoreDatasource @Inject constructor() {
    private val db = Firebase.firestore

    suspend fun getRadicalById(id: Int) =
        db.collection(radicalsCollectionId)
            .document(id.toString())
            .get()
            .data(Radical.serializer())

    suspend fun getRadicals(
        currentOffset: Int,
        numberOfItems: Int
    ) = db.collection(radicalsCollectionId)
        .fetchPaginated(
            orderBy = "Id",
            startAfter = currentOffset,
            limit = numberOfItems
        ) { document ->
            document.data(Radical.serializer())
        }

    suspend fun getRadicalsForKanji(kanjiId: Int) =
        db.collection(kanjiRadicalsCollectionId)
            .document(kanjiId.toString())
            .get()
            .data(KanjiWithRadicals.serializer())
            .radicals
}