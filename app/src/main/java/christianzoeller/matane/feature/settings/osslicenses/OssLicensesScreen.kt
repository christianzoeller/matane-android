package christianzoeller.matane.feature.settings.osslicenses

import android.os.Parcelable
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.parcelize.Parcelize

@Parcelize
data class LibraryOverview(
    val libraryName: String,
    val libraryId: String,
    val licenses: List<String>
) : Parcelable

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun OssLicensesScreen() {
    val navigator = rememberListDetailPaneScaffoldNavigator<LibraryOverview>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    Scaffold { contentPadding ->
        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                AnimatedPane {
                    OssLicensesList(
                        onLibraryClick = { license ->
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, license)
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            },
            detailPane = {
                AnimatedPane {
                    when (val library = navigator.currentDestination?.content) {
                        null -> OssLicenseDetailEmpty(modifier = Modifier.fillMaxSize())
                        else -> OssLicenseDetail(
                            selectedLibrary = library,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            },
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@Composable
private fun OssLicensesList(
    onLibraryClick: (LibraryOverview) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OssLicenseInfoMocks.info.libraries.forEach { library ->
            ListItem(
                headlineContent = { Text(library.name) },
                modifier = Modifier.clickable {
                    onLibraryClick(
                        LibraryOverview(
                            libraryName = library.name,
                            libraryId = library.uniqueId,
                            licenses = library.licenses.map { it.name }
                        )
                    )
                }
            )
        }
    }
}

@Composable
private fun OssLicenseDetail(
    selectedLibrary: LibraryOverview,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = selectedLibrary.libraryName)
        Text(text = selectedLibrary.licenses.joinToString())
    }
}

@Composable
private fun OssLicenseDetailEmpty(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text("Nothing selected")
    }
}