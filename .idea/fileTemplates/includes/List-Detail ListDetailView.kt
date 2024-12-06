@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import christianzoeller.matane.ui.extensions.scrollToTop
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.ExpandedPreview
import christianzoeller.matane.ui.tooling.MatanePreview
import christianzoeller.matane.ui.tooling.MediumPreview
import ${STATE_PACKAGE_NAME}.${OVERVIEWSTATE}
import ${STATE_PACKAGE_NAME}.${DETAILSTATE}
import ${STATE_PACKAGE_NAME}.Selected${NAME}ListItem

#parse("File Header.java")
@Composable
fun ${LISTDETAILVIEW}(
    overviewData: ${OVERVIEWSTATE}.Content,
    detailState: ${DETAILSTATE},
    onItemClick: (Selected${NAME}ListItem) -> Unit,
    onLoadMore: () -> Unit,
    listDetailNavigator: ThreePaneScaffoldNavigator<Selected${NAME}ListItem> = rememberListDetailPaneScaffoldNavigator<Selected${NAME}ListItem>()
) {
    BackHandler(listDetailNavigator.canNavigateBack()) {
        listDetailNavigator.navigateBack()
    }
    
    val listState = rememberLazyListState()
    val detailScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    
    ListDetailPaneScaffold(
        directive = listDetailNavigator.scaffoldDirective,
        value = listDetailNavigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                ${LISTVIEW}(
                    data = overviewData,
                    listState = listState,
                    onItemClick = { item ->
                        onItemClick(item)
                        listDetailNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
                        detailScrollState.scrollToTop(coroutineScope)
                    },
                    onLoadMore = onLoadMore
                )
            }
        },
        detailPane = {
            val contentModifier = Modifier
                .verticalScroll(detailScrollState)
                .fillMaxSize()
        
            AnimatedPane {
                when (detailState) {
                    is ${DETAILSTATE}.NoSelection -> TODO()
                    
                    is ${DETAILSTATE}.Loading -> TODO()
                    
                    is ${DETAILSTATE}.Data -> TODO()
                    
                    is ${DETAILSTATE}.Error -> TODO()
                }
            }
        }
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun ${LISTDETAILVIEW}_Loading_Preview() = MatanePreview {
    ${LISTDETAILVIEW}(
        overviewData = ${OVERVIEWSTATE}.Loading,
        detailState = ${DETAILSTATE}.Loading,
        onItemClick = {},
        onLoadMore = {}
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun ${LISTDETAILVIEW}_Content_Preview() = MatanePreview {
    ${LISTDETAILVIEW}(
        overviewData = ${OVERVIEWSTATE}.Data(
            items = TODO()
        ),
        detailState = ${DETAILSTATE}.Data(
            item = TODO()
        ),
        onItemClick = {},
        onLoadMore = {}
    )
}

@ExpandedPreview
@MediumPreview
@CompactPreview
@Composable
private fun ${LISTDETAILVIEW}_Error_Preview() = MatanePreview {
    ${LISTDETAILVIEW}(
        overviewData = ${OVERVIEWSTATE}.Data(
            items = TODO()
        ),
        detailState = ${DETAILSTATE}.Error,
        onItemClick = {},
        onLoadMore = {}
    )
}