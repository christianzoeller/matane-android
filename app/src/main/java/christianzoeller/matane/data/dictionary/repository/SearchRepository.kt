package christianzoeller.matane.data.dictionary.repository

import christianzoeller.matane.common.extensions.runRequest
import christianzoeller.matane.common.model.RequestState
import christianzoeller.matane.data.dictionary.datasource.SearchHttpDatastore
import christianzoeller.matane.data.dictionary.model.VocabularyOverview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchHttpDatastore: SearchHttpDatastore
) {
    fun search(query: String): Flow<RequestState<List<VocabularyOverview>>> {
        require(query.isNotBlank())

        return flow {
            emit(RequestState.Loading)
            emit(runRequest { searchHttpDatastore.search(query) })
        }
    }
}