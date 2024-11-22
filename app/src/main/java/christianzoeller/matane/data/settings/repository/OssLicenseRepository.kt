package christianzoeller.matane.data.settings.repository

import android.content.Context
import christianzoeller.matane.data.settings.model.OssLicenseInfo
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.mikepenz.aboutlibraries.util.parseData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import javax.inject.Inject
import javax.inject.Singleton

private const val librariesAssetsFile = "aboutlibraries.json"

@Singleton
class OssLicensesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    var ossLicenseInfo: OssLicenseInfo? = null
        private set

    init {
        ossLicenseInfo = loadLicenseInformation()
    }

    fun getLibraryById(id: String) = ossLicenseInfo
        ?.libraries
        ?.firstOrNull { it.uniqueId == id }

    fun getLicenseByName(name: String) = ossLicenseInfo
        ?.licenses
        ?.firstOrNull { it.name == name }

    private fun loadLicenseInformation() =
        try {
            context.assets
                .open(librariesAssetsFile)
                .bufferedReader()
                .use { reader ->
                    val fileContent = reader.readText()
                    val parsed = parseData(fileContent)
                    OssLicenseInfo(
                        libraries = parsed.libraries
                            .sortedBy { it.name.lowercase() }
                            .toImmutableList(),
                        licenses = parsed.licenses.toImmutableSet()
                    )
                }
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            null
        }
}