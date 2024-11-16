package christianzoeller.matane

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import christianzoeller.matane.data.settings.model.UiMode
import christianzoeller.matane.data.settings.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    preferencesRepository: PreferencesRepository
) : ViewModel() {
    val uiModeState: StateFlow<UiMode> = preferencesRepository.uiModeFlow.stateIn(
        scope = viewModelScope,
        initialValue = UiMode.UseSystemSettings,
        started = SharingStarted.WhileSubscribed(
            stopTimeout = 5.seconds
        )
    )
}