package christianzoeller.matane.data.dictionary.repository

import christianzoeller.matane.common.extensions.runRequest
import christianzoeller.matane.common.model.RequestState
import christianzoeller.matane.data.dictionary.datasource.RadicalFirestoreDatasource
import christianzoeller.matane.data.dictionary.model.RadicalInKanji
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RadicalRepository @Inject constructor(
    private val radicalFirestoreDatasource: RadicalFirestoreDatasource
) {
    fun getRadicalsForKanji(kanjiLiteral: String): Flow<RequestState<List<RadicalInKanji>?>> {
        require(kanjiLiteral.length == 1)

        return flow {
            emit(RequestState.Loading)

            val id = kanjiLiteral.first().code
            emit(
                runRequest {
                    radicalFirestoreDatasource.getRadicalsForKanji(id)
                }
            )
        }
    }
}