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
    private val _overviewState = MutableStateFlow<OssLicensesOverviewState>(
        OssLicensesOverviewState.Loading
    )
    val overviewState = _overviewState.asStateFlow()

    private val _detailState = MutableStateFlow<OssLicensesDetailState>(OssLicensesDetailState.NoSelection)
    val detailState = _detailState.asStateFlow()

    init {
        loadLicenseInfo()

        viewModelScope.launch {
            overviewState.collect {
                _detailState.value = when (it) {
                    is OssLicensesOverviewState.Loading -> OssLicensesDetailState.NoSelection
                    is OssLicensesOverviewState.Data -> OssLicensesDetailState.NoSelection
                    is OssLicensesOverviewState.Error -> OssLicensesDetailState.Error
                }
            }
        }
    }

    private fun loadLicenseInfo() {
        viewModelScope.launch {
            // Add an artificial loading time to create the illusion that
            // something happens (and to show off the skeleton loading)
            delay(0.4.seconds)

            _overviewState.value = when (val info = ossLicensesRepository.ossLicenseInfo) {
                null -> OssLicensesOverviewState.Error
                else -> OssLicensesOverviewState.Data(ossLicenseInfo = info)
            }
        }
    }

    fun onLibraryClick(libraryOverview: LibraryOverview) {
        if (_overviewState.value !is OssLicensesOverviewState.Data) return

        val library = ossLicensesRepository.getLibraryById(libraryOverview.libraryId)
        val licenses = libraryOverview.licenses.mapNotNull {
            ossLicensesRepository.getLicenseByName(it)
        }

        _detailState.value = when (library) {
            null -> OssLicensesDetailState.Error
            else -> OssLicensesDetailState.Content(
                library = library,
                licenses = licenses
            )
        }
    }
}