package christianzoeller.matane.feature.settings.osslicenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import christianzoeller.matane.data.settings.repository.OssLicensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class OssLicensesViewModel @Inject constructor(
    private val ossLicensesRepository: OssLicensesRepository
) : ViewModel() {
    private val _state = MutableStateFlow<OssLicensesState>(
        OssLicensesState.Loading
    )
    val state = _state.asStateFlow()

    init {
        loadLicenseInfo()
    }

    private fun loadLicenseInfo() {
        viewModelScope.launch {
            // Add an artificial loading time to create the illusion that
            // something happens (and to show off the skeleton loading)
            delay(0.4.seconds)

            _state.value = when (val info = ossLicensesRepository.ossLicenseInfo) {
                null -> OssLicensesState.Error

                else -> OssLicensesState.Data(
                    overviewData = OssLicensesState.Content.Overview(info)
                )
            }
        }
    }

    fun onLibraryClick(libraryOverview: LibraryOverview) {
        val current = _state.value as? OssLicensesState.Data ?: return

        val library = ossLicensesRepository.getLibraryById(libraryOverview.libraryId)
        val licenses = libraryOverview.licenses.mapNotNull {
            ossLicensesRepository.getLicenseByName(it)
        }

        _state.value = when (library) {
            null -> current.copy(
                detailData = null,
                loadDetailError = true
            )

            else -> current.copy(
                detailData = OssLicensesState.Content.Detail(
                    library = library,
                    licenses = licenses
                ),
                loadDetailError = false
            )
        }
    }
}