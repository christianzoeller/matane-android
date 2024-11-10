#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.matane.R
import christianzoeller.matane.styleguide.components.DefaultTopAppBar
import christianzoeller.matane.ui.theme.MataneTheme
import christianzoeller.matane.ui.tooling.CompactPreview
import ${PACKAGE_NAME}.ui.${LISTDETAILVIEW}

#parse("File Header.java")
@Composable
fun ${NAME}Screen(
    viewModel: ${VIEWMODEL},
    onNavigateUp: () -> Unit
) {
    val overviewState by viewModel.overviewState.collectAsStateWithLifecycle()
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()

    Screen(
        overviewState = overviewState,
        detailState = detailState,
        onItemClick = viewModel::onItemClick,
        onLoadMore = viewModel::onLoadMore,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun Screen(
    overviewState: ${OVERVIEWSTATE},
    detailState: ${DETAILSTATE},
    onItemClick: (Selected${NAME}ListItem) -> Unit,
    onLoadMore: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val listDetailNavigator = rememberListDetailPaneScaffoldNavigator<Selected${NAME}ListItem>()

    Column {
        DefaultTopAppBar(
            onNavigateUp = {
                if (listDetailNavigator.canNavigateBack()) {
                    listDetailNavigator.navigateBack()
                } else {
                    onNavigateUp()
                }
            },
            title = TODO()
        )

        when (overviewState) {
            is ${OVERVIEWSTATE}.Content -> ${LISTDETAILVIEW}(
                overviewData = overviewState,
                detailState = detailState,
                onItemClick = onItemClick,
                onLoadMore = onLoadMore,
                listDetailNavigator = listDetailNavigator
            )

            is ${OVERVIEWSTATE}.Error -> TODO()
        }
    }
}

@CompactPreview
@Composable
private fun ${NAME}Screen_Loading_Preview() = MataneTheme {
    Screen(
        overviewState = ${OVERVIEWSTATE}.Loading,
        detailState = ${DETAILSTATE}.NoSelection,
        onItemClick = {},
        onLoadMore = {},
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun ${NAME}Screen_Content_Preview() = MataneTheme {
    Screen(
        overviewState = ${OVERVIEWSTATE}.Data(
            items = TODO()
        ),
        detailState = ${DETAILSTATE}.Data(
            item = TODO()
        ),
        onItemClick = {},
        onLoadMore = {},
        onNavigateUp = {}
    )
}

@CompactPreview
@Composable
private fun ${NAME}Screen_Error_Preview() = MataneTheme {
    Screen(
        overviewState = ${OVERVIEWSTATE}.Error,
        detailState = ${DETAILSTATE}.Error,
        onItemClick = {},
        onLoadMore = {},
        onNavigateUp = {}
    )
}