package christianzoeller.matane.feature.settings.osslicenses

import androidx.lifecycle.ViewModel
import christianzoeller.matane.data.settings.repository.OssLicensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OssLicensesViewModel @Inject constructor(
    private val ossLicensesRepository: OssLicensesRepository
) : ViewModel() {
    private val _state = MutableStateFlow<OssLicensesState>(
        OssLicensesState.Loading
    )
    val state = _state.asStateFlow()

    init {
        _state.value = when (val info = ossLicensesRepository.ossLicenseInfo) {
            null -> OssLicensesState.Error
            else -> OssLicensesState.Data(
                overviewData = OssLicensesState.Data.Overview(info)
            )
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
                detailData = OssLicensesState.Data.Detail(
                    library = library,
                    licenses = licenses
                ),
                loadDetailError = false
            )
        }
    }
}