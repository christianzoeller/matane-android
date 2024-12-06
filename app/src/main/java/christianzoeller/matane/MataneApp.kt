package christianzoeller.matane

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import christianzoeller.matane.navigation.MataneNavHost
import christianzoeller.matane.navigation.TopLevelDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MataneApp(
    appState: MataneAppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo()
) {
    val currentDestination = appState.currentDestination
    val layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
        adaptiveInfo = windowAdaptiveInfo
    )

    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = colorScheme.onSecondaryContainer,
            unselectedIconColor = colorScheme.onSurface,
            selectedTextColor = colorScheme.secondary,
            unselectedTextColor = colorScheme.onSurface,
            indicatorColor = colorScheme.secondaryContainer
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = colorScheme.onSecondaryContainer,
            unselectedIconColor = colorScheme.onSurface,
            selectedTextColor = colorScheme.secondary,
            unselectedTextColor = colorScheme.onSurface,
            indicatorColor = colorScheme.secondaryContainer
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = colorScheme.onSecondaryContainer,
            unselectedIconColor = colorScheme.onSurface,
            selectedTextColor = colorScheme.secondary,
            unselectedTextColor = colorScheme.onSurface
        ),
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            appState.topLevelDestinations.forEach {
                item(
                    selected = currentDestination?.isTopLevelDestinationInHierarchy(it) ?: false,
                    onClick = { appState.navigateToTopLevelDestination(it) },
                    icon = {
                        Icon(
                            painter = painterResource(id = it.icon),
                            contentDescription = stringResource(id = it.iconDescription)
                        )
                    },
                    label = { Text(text = stringResource(id = it.label)) },
                    colors = navigationSuiteItemColors
                )
            }
        },
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = colorScheme.background,
            navigationRailContainerColor = colorScheme.background,
            navigationDrawerContainerColor = colorScheme.background
        ),
        layoutType = layoutType
    ) {
        Scaffold(
            modifier = modifier,
            containerColor = Color.Transparent,
            contentColor = colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0 , 0)
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        insets = WindowInsets.safeDrawing.only(
                            sides = WindowInsetsSides.Horizontal
                        )
                    )
            ) {
                MataneNavHost(
                    appState = appState
                )
            }
        }
    }
}

private fun NavDestination.isTopLevelDestinationInHierarchy(
    topLevelDestination: TopLevelDestination
) = hierarchy.any { destination ->
    destination.hasRoute(topLevelDestination::class)
}