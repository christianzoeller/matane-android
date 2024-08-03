package christianzoeller.matane

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import christianzoeller.matane.navigation.MataneNavHost
import christianzoeller.matane.navigation.TopLevelDestination

@Composable
fun MataneApp(
    appState: MataneAppState,
    modifier: Modifier = Modifier
) {
    val currentDestination = appState.currentDestination

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
                    label = { Text(text = stringResource(id = it.label)) }
                )
            }
        },
        modifier = modifier
    ) {
        MataneNavHost(
            appState = appState
        )
    }
}

private fun NavDestination.isTopLevelDestinationInHierarchy(
    topLevelDestination: TopLevelDestination
) = hierarchy.any { destination ->
    destination.route?.contains(topLevelDestination.name, true) ?: false
}