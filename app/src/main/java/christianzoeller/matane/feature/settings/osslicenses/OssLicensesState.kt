package christianzoeller.matane.feature.settings.osslicenses

import android.os.Parcelable
import christianzoeller.matane.data.settings.model.OssLicenseInfo
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.License
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.parcelize.Parcelize

sealed interface OssLicensesState {
    sealed class Content(
        open val overviewData: Overview,
        open val detailData: Detail?,
    ) : OssLicensesState {
        data class Overview(val ossLicenseInfo: OssLicenseInfo)
        data class Detail(
            val library: Library,
            val licenses: List<License>
        )
    }

    data object Loading : Content(
        overviewData = Overview(
            ossLicenseInfo = OssLicenseInfo(
                libraries = List(15) { skeletonListItem(it) }.toImmutableList(),
                licenses = persistentSetOf()
            )
        ),
        detailData = null
    )

    data class Data(
        override val overviewData: Overview,
        override val detailData: Detail? = null,
        val loadDetailError: Boolean = false
    ) : Content(
        overviewData = overviewData,
        detailData = detailData
    )

    data object Error : OssLicensesState
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