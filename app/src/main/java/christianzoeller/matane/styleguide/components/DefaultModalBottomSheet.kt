package christianzoeller.matane.styleguide.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultModalBottomSheet(
    onDismissRequest: () -> Unit,
    @StringRes title: Int,
    sheetState: SheetState = rememberModalBottomSheetState(),
    content: @Composable ColumnScope.() -> Unit
) {
    DefaultModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Text(
            text = stringResource(id = title),
            modifier = Modifier.padding(horizontal = 16.dp),
            color = colorScheme.primary,
            style = typography.titleLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 16.dp, end = 16.dp,
                    bottom = 32.dp
                ),
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultModalBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = Modifier.windowInsetsPadding(
            insets = WindowInsets.safeDrawing.only(
                sides = WindowInsetsSides.Top
            )
        ),
        sheetState = sheetState,
        containerColor = colorScheme.background,
        contentColor = colorScheme.onSurface,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = colorScheme.primaryContainer
            )
        },
        scrimColor = colorScheme.scrim,
        content = content
    )
}
