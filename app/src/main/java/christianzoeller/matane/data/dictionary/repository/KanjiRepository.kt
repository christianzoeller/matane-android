package christianzoeller.matane.data.dictionary.repository

import christianzoeller.matane.common.model.RequestState
import christianzoeller.matane.data.dictionary.datasource.KanjiFirestoreDatasource
import christianzoeller.matane.data.dictionary.model.Kanji
import christianzoeller.matane.data.dictionary.model.KanjiInContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KanjiRepository @Inject constructor(
    private val kanjiFirestoreDatasource: KanjiFirestoreDatasource
) {
    suspend fun getKanjiByLiteral(literal: String): Flow<RequestState<Kanji?>> {
        require(literal.length == 1)

        return flow {
            emit(RequestState.Loading)

            val id = literal.first().code
            emit(kanjiFirestoreDatasource.getKanjiById(id))
        }
    }

    /**
     * Fetches a list of kanji in the order in which they are taught in school.
     *
     * The data is paginated. [lastKanji] will be used as a cursor for the
     * pagination.
     *
     * @param lastKanji The last kanji of a previous call to this function. Is
     * used for paging and should be omitted for the initial call.
     * @param numberOfItems The maximum number of items to be returned by this
     * function.
     */
    suspend fun getKanjiByGrade(
        lastKanji: KanjiInContext?,
        numberOfItems: Int
    ) = flow {
        emit(RequestState.Loading)
        emit(
            kanjiFirestoreDatasource.getKanjiByGrade(
                lastKanji = lastKanji,
                numberOfItems = numberOfItems
            )
        )
    }

    suspend fun getMostFrequentKanji(
        currentOffset: Int,
        numberOfItems: Int
    ) = flow {
        emit(RequestState.Loading)
        emit(
            kanjiFirestoreDatasource.getMostFrequentKanji(
                currentOffset = currentOffset,
                numberOfItems = numberOfItems
            )
        )
    }
}