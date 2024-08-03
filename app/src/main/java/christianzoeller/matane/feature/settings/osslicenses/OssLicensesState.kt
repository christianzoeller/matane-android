package christianzoeller.matane.feature.settings.osslicenses

import android.os.Parcelable
import christianzoeller.matane.data.settings.model.OssLicenseInfo
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.License
import kotlinx.parcelize.Parcelize

sealed interface OssLicensesState {
    data object Loading : OssLicensesState

    data class Data(
        val overviewData: Overview,
        val detailData: Detail? = null,
        val loadDetailError: Boolean = false
    ) : OssLicensesState {
        data class Overview(val ossLicenseInfo: OssLicenseInfo)
        data class Detail(
            val library: Library,
            val licenses: List<License>
        )
    }

    data object Error : OssLicensesState
}

@Parcelize
data class LibraryOverview(
    val libraryName: String,
    val libraryId: String,
    val licenses: List<String>
) : Parcelable