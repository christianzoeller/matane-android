package christianzoeller.matane.styleguide.effects

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * An effect that can be used inside [LazyColumn] and [LazyRow]. Is triggered
 * when the user scrolls far enough to the end.
 *
 * @param listState The [LazyListState] of the surrounding [LazyColumn] or
 * [LazyRow]
 * @param offsetFromEnd The offset to the end that is considered close. Must
 * be greater than zero.
 * @param onScrolledCloseToEnd The action that should be fired when the effect
 * is triggered.
 */
@Composable
fun OnScrollCloseToEndEffect(
    listState: LazyListState,
    offsetFromEnd: Int,
    onScrolledCloseToEnd: () -> Unit
) {
    require(offsetFromEnd > 0)

    val isCloseToEnd = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val lastIndex = layoutInfo.totalItemsCount - 1
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            lastVisibleItemIndex > 0.coerceAtLeast(lastIndex - offsetFromEnd)
        }
    }

    LaunchedEffect(isCloseToEnd) {
        snapshotFlow { isCloseToEnd.value }
            .distinctUntilChanged()
            .collect { isCloseToEnd ->
                if (isCloseToEnd) {
                    onScrolledCloseToEnd()
                }
            }
    }
}