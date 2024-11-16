package christianzoeller.matane.feature.settings.appearance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import christianzoeller.matane.data.settings.model.UiMode
import christianzoeller.matane.data.settings.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppearanceViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AppearanceState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.uiModeFlow.collect { uiMode ->
                _state.update { current ->
                    current.copy(uiMode = uiMode)
                }
            }
        }
    }

    fun onChangeUiMode(newMode: UiMode) {
        viewModelScope.launch {
            preferencesRepository.changeUiModePreference(newMode)
        }
    }
}