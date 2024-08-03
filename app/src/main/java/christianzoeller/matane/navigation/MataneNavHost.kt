package christianzoeller.matane.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import christianzoeller.matane.MataneAppState
import christianzoeller.matane.feature.dictionary.search.SearchScreen
import christianzoeller.matane.feature.settings.SettingsScreen
import christianzoeller.matane.feature.settings.osslicenses.OssLicensesScreen

@Composable
fun MataneNavHost(
    appState: MataneAppState
) {
    NavHost(
        navController = appState.navController,
        startDestination = TopLevelDestination.Dictionary.name
    ) {
        composable(TopLevelDestination.Dictionary.name) {
            SearchScreen()
        }

        composable(TopLevelDestination.Settings.name) {
            SettingsScreen(
                onOssLicenseClick = { appState.navigate(Destination.OssLicenses) }
            )
        }

        composable<Destination.OssLicenses> {
            OssLicensesScreen()
        }
    }
}