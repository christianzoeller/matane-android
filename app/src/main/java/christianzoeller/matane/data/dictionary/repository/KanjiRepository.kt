package christianzoeller.matane.data.dictionary.repository

import christianzoeller.matane.common.model.RequestState
import christianzoeller.matane.data.dictionary.datasource.KanjiFirestoreDatasource
import christianzoeller.matane.data.dictionary.model.Kanji
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

    suspend fun getKanjiByGrade() = flow {
        emit(RequestState.Loading)
        emit(kanjiFirestoreDatasource.getKanjiByGrade())
    }

    suspend fun getMostFrequentKanji() = flow {
        emit(RequestState.Loading)
        emit(kanjiFirestoreDatasource.getMostFrequentKanji())
    }
}