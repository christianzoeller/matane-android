package christianzoeller.matane.feature.dictionary.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import christianzoeller.matane.common.model.RequestResult
import christianzoeller.matane.common.model.RequestState
import christianzoeller.matane.data.dictionary.repository.SearchRepository
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _overviewState = MutableStateFlow<SearchOverviewState>(
        SearchOverviewState.Empty
    )
    val overviewState = _overviewState.asStateFlow()

    private val _detailState = MutableStateFlow<SearchDetailState>(
        SearchDetailState.Empty
    )
    val detailState = _detailState.asStateFlow()

    private val _queryState = MutableStateFlow<String>("")
    val queryState = _queryState.asStateFlow()

    init {
        viewModelScope.launch {
            overviewState.collect {
                _detailState.value = when (it) {
                    is SearchOverviewState.Empty -> SearchDetailState.Empty
                    is SearchOverviewState.Loading -> SearchDetailState.NoSelection
                    is SearchOverviewState.Data -> _detailState.value
                    is SearchOverviewState.Error -> SearchDetailState.Error
                }
            }
        }
    }

    fun onEnterQuery(newValue: String) {
        _queryState.value = newValue
    }

    fun search(query: String) {
        viewModelScope.launch {
            searchRepository.search(query).collect { requestState ->
                _overviewState.value = when (requestState) {
                    is RequestState.Loading -> SearchOverviewState.Loading

                    is RequestResult.Success -> SearchOverviewState.Data(
                        items = requestState.data
                    )

                    is RequestResult.Error -> {
                        Firebase.crashlytics.recordException(requestState.exception)
                        SearchOverviewState.Error
                    }
                }
            }
        }
    }

    fun onItemClick(selectedListItem: SelectedSearchListItem) {
        if (_overviewState.value !is SearchOverviewState.Data) return


    }
}

val SearchOverviewState.searchEnabled: Boolean
    get() = when (this) {
        is SearchOverviewState.Data -> true
        is SearchOverviewState.Loading -> false
        is SearchOverviewState.Empty -> true
        is SearchOverviewState.Error -> true
    }