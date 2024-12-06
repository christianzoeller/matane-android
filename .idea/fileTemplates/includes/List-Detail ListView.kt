#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import christianzoeller.matane.styleguide.effects.OnScrollCloseToEndEffect
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview
import ${STATE_PACKAGE_NAME}.${OVERVIEWSTATE}
import ${STATE_PACKAGE_NAME}.Selected${NAME}ListItem

#parse("File Header.java")
@Composable
fun ${LISTVIEW}(
    data: ${OVERVIEWSTATE}.Content,
    listState: LazyListState,
    onItemClick: (Selected${NAME}ListItem) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(
            items = data.items
        ) { item ->
        
        }
    }
    
    OnScrollCloseToEndEffect(
        listState = listState,
        offsetFromEnd = 10,
        onScrolledCloseToEnd = onLoadMore
    )
}

@CompactPreview
@Composable
private fun ${LISTVIEW}_Loading_Preview() = MatanePreview {
    ${LISTVIEW}(
        data = ${OVERVIEWSTATE}.Loading,
        listState = rememberLazyListState(),
        onItemClick = {},
        onLoadMore = {}
    )
}

@CompactPreview
@Composable
private fun ${LISTVIEW}_Content_Preview() = MatanePreview {
    ${LISTVIEW}(
        data = ${OVERVIEWSTATE}.Data(
            items = TODO()
        ),
        listState = rememberLazyListState(),
        onItemClick = {},
        onLoadMore = {}
    )
}
