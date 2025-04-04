package christianzoeller.matane.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import christianzoeller.matane.MataneAppState
import christianzoeller.matane.feature.dictionary.kanji.KanjiListType
import christianzoeller.matane.feature.dictionary.kanji.KanjiScreen
import christianzoeller.matane.feature.dictionary.kanji.KanjiViewModel
import christianzoeller.matane.feature.dictionary.radical.RadicalScreen
import christianzoeller.matane.feature.dictionary.radical.RadicalViewModel
import christianzoeller.matane.feature.dictionary.HomeScreen
import christianzoeller.matane.feature.dictionary.search.SearchScreen
import christianzoeller.matane.feature.dictionary.search.SearchViewModel
import christianzoeller.matane.feature.settings.SettingsScreen
import christianzoeller.matane.feature.settings.acknowledgements.AcknowledgementsScreen
import christianzoeller.matane.feature.settings.appearance.AppearanceScreen
import christianzoeller.matane.feature.settings.appearance.AppearanceViewModel
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
            startDestination = Destination.Home
        ) {
            composable<Destination.Home> {
                HomeScreen(
                    onSearch = {
                        appState.navigate(Destination.Search)
                    },
                    onKanjiCardClick = {
                        appState.navigate(
                            Destination.Kanji(KanjiListType.ByFrequency)
                        )
                    },
                    onRadicalCardClick = {
                        appState.navigate(Destination.Radical)
                    }
                )
            }

            composable<Destination.Search> {
                val viewModel = hiltViewModel<SearchViewModel>()

                SearchScreen(
                    viewModel = viewModel,
                    onNavigateUp = { appState.navigateUp() }
                )
            }

            composable<Destination.Kanji> {
                val viewModel = hiltViewModel<KanjiViewModel>()

                KanjiScreen(
                    viewModel = viewModel,
                    onNavigateUp = { appState.navigateUp() }
                )
            }

            composable<Destination.Radical> {
                val viewModel = hiltViewModel<RadicalViewModel>()

                RadicalScreen(
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
                    onAppearanceClick = { appState.navigate(Destination.AppearanceSettings) },
                    onAcknowledgementsClick = { appState.navigate(Destination.Acknowledgements) },
                    onOssLicenseClick = { appState.navigate(Destination.OssLicenses) }
                )
            }

            composable<Destination.AppearanceSettings> {
                val viewModel = hiltViewModel<AppearanceViewModel>()

                AppearanceScreen(
                    viewModel = viewModel,
                    onNavigateUp = { appState.navigateUp() }
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