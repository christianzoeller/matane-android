package christianzoeller.matane.feature.settings.osslicenses

import android.os.Parcelable
import christianzoeller.matane.data.settings.model.OssLicenseInfo
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.License
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.parcelize.Parcelize

sealed interface OssLicensesOverviewState {
    sealed class Content(
        open val ossLicenseInfo: OssLicenseInfo
    ) : OssLicensesOverviewState

    data object Loading : Content(
        ossLicenseInfo = OssLicenseInfo(
            libraries = List(15) { skeletonListItem(it) }.toImmutableList(),
            licenses = persistentSetOf()
        )
    )

    data class Data(
        override val ossLicenseInfo: OssLicenseInfo
    ) : Content(
        ossLicenseInfo = ossLicenseInfo
    )

    data object Error : OssLicensesOverviewState
}

sealed interface OssLicensesDetailState {
    data object NoSelection : OssLicensesDetailState

    data class Content(
        val library: Library,
        val licenses: List<License>
    ) : OssLicensesDetailState

    data object Error : OssLicensesDetailState
}

@Parcelize
data class LibraryOverview(
    val libraryName: String,
    val libraryId: String,
    val licenses: List<String>
) : Parcelable

private fun skeletonListItem(id: Int) = Library(
    uniqueId = id.toString(),
    artifactVersion = null,
    name = "Compose Material Components",
    description = null,
    website = null,
    developers = persistentListOf(),
    organization = null,
    scm = null
)