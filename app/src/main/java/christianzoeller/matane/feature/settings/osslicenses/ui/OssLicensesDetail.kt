package christianzoeller.matane.feature.settings.osslicenses.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import christianzoeller.matane.R
import christianzoeller.matane.feature.settings.osslicenses.OssLicensesState
import christianzoeller.matane.feature.settings.osslicenses.model.OssLicenseInfoMocks
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.License

@Composable
fun OssLicensesDetail(
    data: OssLicensesState.Data.Detail,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        LibrarySectionHeader(library = data.library)
        Spacer(modifier = Modifier.height(24.dp))
        LibrarySection(library = data.library)
        Spacer(modifier = Modifier.height(32.dp))
        LicenseSectionHeader()
        Spacer(modifier = Modifier.height(24.dp))
        LicenseSection(licenses = data.licenses)
    }
}

@Composable
private fun LibrarySectionHeader(library: Library) {
    Text(
        text = library.name,
        style = MaterialTheme.typography.headlineMedium
    )

    when {
        library.organization != null -> {
            Spacer(modifier = Modifier.height(16.dp))

            when (val url = library.organization!!.url) {
                null -> Text(
                    text = library.organization!!.name,
                    style = MaterialTheme.typography.bodySmall
                )

                else -> TextLink(
                    text = library.organization!!.name,
                    url = url
                )
            }
        }

        else -> {
            library.developers
                .mapNotNull { it.name }
                .takeIf { it.isNotEmpty() }
                ?.joinToString()
                ?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
        }
    }
}

@Composable
private fun LibrarySection(library: Library) {
    library.description?.let {
        Text(text = it)
    }

    library.website?.let {
        Spacer(modifier = Modifier.height(8.dp))
        TextLink(text = it, url = it)
    }
}

@Composable
private fun LicenseSectionHeader() {
    Text(
        text = stringResource(id = R.string.oss_licenses_detail_license_header),
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun LicenseSection(licenses: List<License>) {
    licenses.forEachIndexed { index, license ->
        if (index != 0) {
            Spacer(modifier = Modifier.height(32.dp))
        }

        Text(
            text = license.name,
            style = MaterialTheme.typography.titleMedium
        )

        license.url?.let {
            Spacer(modifier = Modifier.height(16.dp))
            TextLink(
                text = it,
                url = it,
                style = MaterialTheme.typography.bodySmall
            )
        }

        license.licenseContent?.let {
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = it)
        }
    }
}

@Composable
private fun TextLink(
    text: String,
    url: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val context = LocalContext.current

    Text(
        text = text,
        modifier = Modifier.clickable {
            context.openInExternalBrowser(url)
        },
        textDecoration = TextDecoration.Underline,
        style = style
    )
}

@CompactPreview
@Composable
private fun OssLicensesDetail_Preview() = MataneTheme {
    OssLicensesDetail(
        data = OssLicensesState.Data.Detail(
            library = OssLicenseInfoMocks.library,
            licenses = listOf(OssLicenseInfoMocks.license)
        ),
        modifier = Modifier.padding(16.dp)
    )
}

private fun Context.openInExternalBrowser(link: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    try {
        startActivity(browserIntent)
    } catch (e: Exception) {
        Log.e("Matane", "Failed to open external link $link: $e")
    }
}