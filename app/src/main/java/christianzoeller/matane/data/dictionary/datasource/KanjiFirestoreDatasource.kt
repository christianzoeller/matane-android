package christianzoeller.matane.data.dictionary.datasource

import christianzoeller.matane.common.extensions.fetchPaginated
import christianzoeller.matane.data.dictionary.model.Kanji
import christianzoeller.matane.data.dictionary.model.KanjiInContext
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.firestore
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
            .data(Kanji.serializer())

    suspend fun getKanjiByGrade(
        lastKanji: KanjiInContext?,
        numberOfItems: Int
    ): List<KanjiInContext> {
        val gradeField = FieldPath("Priority")
        val idField = FieldPath("Id")

        val collectionOrdered = db
            .collection(kanjiByGradeCollectionId)
            .orderBy(gradeField)
            .orderBy(idField)

        val collectionWithCorrectStart = if (lastKanji != null) {
            collectionOrdered.startAfter(lastKanji.priority as Any, lastKanji.id)
        } else {
            collectionOrdered
        }

        return collectionWithCorrectStart
            .limit(numberOfItems.toLong())
            .get()
            .documents
            .map { it.data(KanjiInContext.serializer()) }
    }

    suspend fun getMostFrequentKanji(
        currentOffset: Int,
        numberOfItems: Int
    ): List<KanjiInContext> {
        val frequencyStartValue = currentOffset.toString().padStart(4)
        return db.collection(kanjiByFrequencyCollectionId)
            .fetchPaginated(
                orderBy = "Priority",
                startAfter = frequencyStartValue,
                limit = numberOfItems
            ) { document ->
                document.data(KanjiInContext.serializer())
            }
    }
}