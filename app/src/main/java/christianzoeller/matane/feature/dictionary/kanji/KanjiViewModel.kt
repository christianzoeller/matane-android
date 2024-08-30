package christianzoeller.matane.feature.dictionary.kanji

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import christianzoeller.matane.common.extensions.combineRequestFlows
import christianzoeller.matane.common.model.RequestResult
import christianzoeller.matane.common.model.RequestState
import christianzoeller.matane.data.dictionary.repository.KanjiRepository
import christianzoeller.matane.data.dictionary.repository.RadicalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KanjiViewModel @Inject constructor(
    private val kanjiRepository: KanjiRepository,
    private val radicalRepository: RadicalRepository
) : ViewModel() {
    private val _overviewState = MutableStateFlow<KanjiOverviewState>(KanjiOverviewState.Loading)
    val overviewState = _overviewState.asStateFlow()

    private val _detailState = MutableStateFlow<KanjiDetailState>(KanjiDetailState.NoSelection)
    val detailState = _detailState.asStateFlow()

    init {
        loadMostFrequentKanji()

        viewModelScope.launch {
            overviewState.collect {
                _detailState.value = when (it) {
                    is KanjiOverviewState.Loading -> KanjiDetailState.NoSelection
                    is KanjiOverviewState.Data -> KanjiDetailState.NoSelection
                    is KanjiOverviewState.Error -> KanjiDetailState.Error
                }
            }
        }
    }

    private fun loadMostFrequentKanji() {
        viewModelScope.launch {
            kanjiRepository.getMostFrequentKanji().collect { requestState ->
                _overviewState.value = when (requestState) {
                    is RequestState.Loading -> KanjiOverviewState.Loading

                    is RequestResult.Success -> KanjiOverviewState.Data(
                        kanjiList = requestState.data,
                    )

                    is RequestResult.Error -> KanjiOverviewState.Error
                }
            }
        }
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
                        if (requestState.data.first != null && requestState.data.second != null) {
                            KanjiDetailState.Data(
                                kanji = requestState.data.first!!,
                                radicals = requestState.data.second!!
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
}