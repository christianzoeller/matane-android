package christianzoeller.matane.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
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
        startDestination = TopLevelDestination.Dictionary
    ) {
        navigation<TopLevelDestination.Dictionary>(
            startDestination = Destination.Search
        ) {
            composable<Destination.Search> {
                SearchScreen()
            }
        }

        navigation<TopLevelDestination.Settings>(
            startDestination = Destination.Settings
        ) {
            composable<Destination.Settings> {
                SettingsScreen(
                    onOssLicenseClick = { appState.navigate(Destination.OssLicenses) }
                )
            }

            composable<Destination.OssLicenses> {
                OssLicensesScreen()
            }
        }
    }
}