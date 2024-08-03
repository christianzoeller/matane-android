package christianzoeller.matane.feature.settings.osslicenses

import android.net.Uri
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Developer
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.License
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

// TODO move somewhere else
typealias OssLicenseInfo = Libs

object OssLicenseInfoMocks {
    val library = Library(
        uniqueId = "1",
        artifactVersion = null,
        name = "kotlinx.datetime",
        description = "A multiplatform Kotlin library for working with date and time.",
        website = "https://github.com/Kotlin/kotlinx-datetime",
        developers = persistentListOf(
            Developer("Jetbrains Team", Uri.EMPTY.toString())
        ),
        organization = null,
        scm = null
    )

    val license = License(
        name = "Apache-2.0",
        url = "https://github.com/Kotlin/kotlinx-datetime?tab=Apache-2.0-1-ov-file#readme",
        licenseContent = "TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION",
        hash = ""
    )

    val info = OssLicenseInfo(
        libraries = persistentListOf(
            library.copy(uniqueId = "1"),
            library.copy(uniqueId = "2")
        ),
        licenses = persistentSetOf(license)
    )
}