package christianzoeller.matane.feature.dictionary.radical

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import christianzoeller.matane.common.extensions.combineRequestFlows
import christianzoeller.matane.common.model.RequestResult
import christianzoeller.matane.common.model.RequestState
import christianzoeller.matane.data.dictionary.repository.KanjiRepository
import christianzoeller.matane.data.dictionary.repository.RadicalRepository
import christianzoeller.matane.feature.dictionary.kanji.KanjiDetailState
import christianzoeller.matane.feature.dictionary.radical.model.RadicalListItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val pageSize = 50

@HiltViewModel
class RadicalViewModel @Inject constructor(
    private val kanjiRepository: KanjiRepository,
    private val radicalRepository: RadicalRepository
) : ViewModel() {
    private val _overviewState = MutableStateFlow<RadicalOverviewState>(RadicalOverviewState.Loading)
    val overviewState = _overviewState.asStateFlow()

    private val _detailState = MutableStateFlow<RadicalDetailState>(RadicalDetailState.NoSelection)
    val detailState = _detailState.asStateFlow()

    private val _kanjiDetailState = MutableStateFlow<KanjiDetailState?>(null)
    val kanjiDetailState = _kanjiDetailState.asStateFlow()

    init {
        loadInitialData()

        viewModelScope.launch {
            overviewState.collect {
                _detailState.value = when (it) {
                    is RadicalOverviewState.Loading -> RadicalDetailState.Loading
                    is RadicalOverviewState.Data -> _detailState.value
                    is RadicalOverviewState.LoadingMore -> _detailState.value
                    is RadicalOverviewState.Error -> RadicalDetailState.Error
                }
            }
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val requestFlow = radicalRepository.getRadicals(
                currentOffset = 0,
                numberOfItems = pageSize
            )

            requestFlow.collect { requestState ->
                _overviewState.value = when (requestState) {
                    is RequestState.Loading -> RadicalOverviewState.Loading

                    is RequestResult.Success -> RadicalOverviewState.Data(
                        radicals = requestState.data.map {
                            RadicalListItemModel(
                                radical = it,
                                isLoading = false
                            )
                        }
                    )

                    is RequestResult.Error -> RadicalOverviewState.Error
                }
            }
        }
    }

    fun onRadicalClick(radicalLiteral: RadicalLiteral) {
        val current = _overviewState.value as? RadicalOverviewState.Data ?: return

        _detailState.value = current.radicals
            .firstOrNull { it.radical.literal == radicalLiteral.value }
            ?.let { RadicalDetailState.Data(it.radical) }
            ?: RadicalDetailState.Error
    }

    fun onLoadMore() {
        val current = _overviewState.value as? RadicalOverviewState.Data ?: return
        val offset = current.radicals
            .lastOrNull()?.radical?.id
            ?: return

        val loadingMoreState = RadicalOverviewState.LoadingMore(
            currentContent = current.radicals
        )

        viewModelScope.launch {
            _overviewState.value = loadingMoreState
            val requestFlow = radicalRepository.getRadicals(
                currentOffset = offset,
                numberOfItems = pageSize
            )

            requestFlow.collect { requestState ->
                _overviewState.value = when (requestState) {
                    is RequestState.Loading -> loadingMoreState

                    is RequestResult.Success -> RadicalOverviewState.Data(
                        radicals = current.radicals + requestState.data.map {
                            RadicalListItemModel(
                                radical = it,
                                isLoading = false
                            )
                        }
                    )

                    // TODO show an error message and display previous content
                    is RequestResult.Error -> RadicalOverviewState.Error
                }
            }
        }
    }

    fun onKanjiClick(literal: String) {
        // TODO copied from KanjiViewModel -> use case?
        viewModelScope.launch {
            combineRequestFlows(
                kanjiRepository.getKanjiByLiteral(literal),
                radicalRepository.getRadicalsForKanji(literal)
            ).collect { requestState ->
                _kanjiDetailState.value = when (requestState) {
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

    fun onDismissKanji() {
        _kanjiDetailState.value = null
    }
}