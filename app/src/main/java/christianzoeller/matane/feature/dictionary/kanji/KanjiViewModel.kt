package christianzoeller.matane.feature.dictionary.kanji

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import christianzoeller.matane.common.extensions.combineRequestFlows
import christianzoeller.matane.common.model.RequestResult
import christianzoeller.matane.common.model.RequestState
import christianzoeller.matane.data.dictionary.repository.KanjiRepository
import christianzoeller.matane.data.dictionary.repository.RadicalRepository
import christianzoeller.matane.feature.dictionary.kanji.model.KanjiListItemModel
import christianzoeller.matane.feature.dictionary.radical.RadicalDetailState
import christianzoeller.matane.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val pageSize = 50

@HiltViewModel
class KanjiViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val kanjiRepository: KanjiRepository,
    private val radicalRepository: RadicalRepository
) : ViewModel() {
    private val initialListType = savedStateHandle
        .toRoute<Destination.Kanji>()
        .listType

    private val _overviewState = MutableStateFlow<KanjiOverviewState>(
        KanjiOverviewState.Loading(listType = initialListType)
    )
    val overviewState = _overviewState.asStateFlow()

    private val _detailState = MutableStateFlow<KanjiDetailState>(KanjiDetailState.NoSelection)
    val detailState = _detailState.asStateFlow()

    private val _radicalDetailState = MutableStateFlow<RadicalDetailState?>(null)
    val radicalDetailState = _radicalDetailState.asStateFlow()

    init {
        loadInitialData(initialListType)

        viewModelScope.launch {
            overviewState.collect {
                _detailState.value = when (it) {
                    is KanjiOverviewState.Loading -> KanjiDetailState.NoSelection
                    is KanjiOverviewState.Data -> _detailState.value
                    is KanjiOverviewState.LoadingMore -> _detailState.value
                    is KanjiOverviewState.Error -> KanjiDetailState.Error
                }
            }
        }
    }

    private fun loadInitialData(listType: KanjiListType) {
        viewModelScope.launch {
            val requestFlow = when (listType) {
                KanjiListType.ByFrequency -> kanjiRepository
                    .getMostFrequentKanji(
                        currentOffset = 0,
                        numberOfItems = pageSize
                    )

                KanjiListType.ByGrade -> kanjiRepository
                    .getKanjiByGrade(
                        lastKanji = null,
                        numberOfItems = pageSize
                    )
            }

            requestFlow.collect { requestState ->
                _overviewState.value = when (requestState) {
                    is RequestState.Loading -> KanjiOverviewState.Loading(
                        listType = listType
                    )

                    is RequestResult.Success -> KanjiOverviewState.Data(
                        kanjiList = requestState.data.map {
                            KanjiListItemModel(
                                kanji = it,
                                isLoading = false
                            )
                        },
                        listType = listType
                    )

                    is RequestResult.Error -> KanjiOverviewState.Error(
                        listType = listType
                    )
                }
            }
        }
    }

    fun onListTypeChange(newListType: KanjiListType) {
        if (newListType == _overviewState.value.listType) return

        // TODO this overrides the data we already loaded, perhaps it makes sense to keep it
        // in memory; perhaps the caching mechanisms of the Firebase SDK make this unnecessary,
        // though
        loadInitialData(newListType)
    }

    fun onKanjiClick(kanjiLiteral: KanjiLiteral) {
        if (_overviewState.value !is KanjiOverviewState.Data) return

        viewModelScope.launch {
            combineRequestFlows(
                kanjiRepository.getKanjiByLiteral(kanjiLiteral.value),
                radicalRepository.getRadicalsForKanji(kanjiLiteral.value)
            ).collect { requestState ->
                _detailState.value = when (requestState) {
                    is RequestState.Loading -> KanjiDetailState.Loading

                    is RequestResult.Success -> {
                        if (requestState.data.first != null) {
                            KanjiDetailState.Data(
                                kanji = requestState.data.first!!,
                                radicals = requestState.data.second
                            )
                        } else {
                            KanjiDetailState.Error
                        }
                    }

                    is RequestResult.Error -> KanjiDetailState.Error
                }
            }
        }
    }

    fun onLoadMore() {
        val current = _overviewState.value as? KanjiOverviewState.Data ?: return

        val loadingMoreState = KanjiOverviewState.LoadingMore(
            currentContent = current.kanjiList,
            listType = current.listType
        )

        viewModelScope.launch {
            _overviewState.value = loadingMoreState
            val requestFlow = when (current.listType) {
                KanjiListType.ByFrequency -> {
                    val offset = current.kanjiList
                        .lastOrNull()?.kanji?.priority
                        ?.trim()
                        ?.toIntOrNull() ?: return@launch

                    kanjiRepository.getMostFrequentKanji(
                        currentOffset = offset,
                        numberOfItems = pageSize
                    )
                }

                KanjiListType.ByGrade -> {
                    val lastKanji = current.kanjiList
                        .lastOrNull()?.kanji ?: return@launch

                    kanjiRepository.getKanjiByGrade(
                        lastKanji = lastKanji,
                        numberOfItems = pageSize
                    )
                }
            }

            requestFlow.collect { requestState ->
                _overviewState.value = when (requestState) {
                    is RequestState.Loading -> loadingMoreState

                    is RequestResult.Success -> KanjiOverviewState.Data(
                        kanjiList = current.kanjiList + requestState.data.map {
                            KanjiListItemModel(
                                kanji = it,
                                isLoading = false
                            )
                        },
                        listType = current.listType
                    )

                    // TODO show an error message and display previous content
                    is RequestResult.Error -> KanjiOverviewState.Error(
                        listType = current.listType
                    )
                }
            }
        }
    }

    fun onRadicalClick(radicalLiteral: String) {
        viewModelScope.launch {
            radicalRepository
                .getRadicalByLiteral(radicalLiteral)
                .collect { requestState ->
                    _radicalDetailState.value = when (requestState) {
                        RequestState.Loading -> RadicalDetailState.Loading

                        is RequestResult.Success -> RadicalDetailState.Data(
                            radical = requestState.data
                        )

                        is RequestResult.Error -> RadicalDetailState.Error
                    }
                }
        }
    }

    fun onDismissRadical() {
        _radicalDetailState.value = null
    }
}