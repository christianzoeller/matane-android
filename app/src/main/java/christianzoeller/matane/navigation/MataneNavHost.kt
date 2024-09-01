package christianzoeller.matane.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import christianzoeller.matane.MataneAppState
import christianzoeller.matane.feature.dictionary.kanji.KanjiScreen
import christianzoeller.matane.feature.dictionary.kanji.KanjiViewModel
import christianzoeller.matane.feature.dictionary.search.SearchScreen
import christianzoeller.matane.feature.settings.SettingsScreen
import christianzoeller.matane.feature.settings.acknowledgements.AcknowledgementsScreen
import christianzoeller.matane.feature.settings.osslicenses.OssLicensesScreen
import christianzoeller.matane.feature.settings.osslicenses.OssLicensesViewModel

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
                SearchScreen(
                    onMostFrequentKanjiCardClick = { appState.navigate(Destination.Kanji) }
                )
            }

            composable<Destination.Kanji> {
                val viewModel = hiltViewModel<KanjiViewModel>()

                KanjiScreen(
                    viewModel = viewModel,
                    onNavigateUp = { appState.navigateUp() }
                )
            }
        }

        navigation<TopLevelDestination.Settings>(
            startDestination = Destination.Settings
        ) {
            composable<Destination.Settings> {
                SettingsScreen(
                    onAcknowledgementsClick = { appState.navigate(Destination.Acknowledgements) },
                    onOssLicenseClick = { appState.navigate(Destination.OssLicenses) }
                )
            }

            composable<Destination.Acknowledgements> {
                AcknowledgementsScreen(
                    onNavigateUp = { appState.navigateUp() }
                )
            }

            composable<Destination.OssLicenses> {
                val viewModel = hiltViewModel<OssLicensesViewModel>()

                OssLicensesScreen(
                    viewModel = viewModel,
                    onNavigateUp = { appState.navigateUp() }
                )
            }
        }
    }
}