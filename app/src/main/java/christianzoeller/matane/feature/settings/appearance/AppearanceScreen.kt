package christianzoeller.matane.feature.settings.appearance

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.matane.R
import christianzoeller.matane.data.settings.model.UiMode
import christianzoeller.matane.styleguide.components.DefaultTopAppBar
import christianzoeller.matane.ui.tooling.CompactPreview
import christianzoeller.matane.ui.tooling.MatanePreview

@Composable
fun AppearanceScreen(
    viewModel: AppearanceViewModel,
    onNavigateUp: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Screen(
        state = state,
        onUiModeClick = viewModel::onChangeUiMode,
        onNavigateUp = onNavigateUp
    )
}

@Composable
private fun Screen(
    state: AppearanceState,
    onUiModeClick: (UiMode) -> Unit,
    onNavigateUp: () -> Unit
) {
    Column {
        DefaultTopAppBar(
            onNavigateUp = onNavigateUp,
            title = R.string.settings_appearance_header
        )

        Content(
            data = state,
            onUiModeClick = onUiModeClick
        )
    }
}

@Composable
private fun Content(
    data: AppearanceState,
    onUiModeClick: (UiMode) -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 48.dp)
    ) {
        Text(
            text = stringResource(R.string.settings_appearance_ui_mode_header),
            modifier = Modifier.padding(horizontal = 16.dp),
            style = typography.titleLarge
        )
        Spacer(modifier = Modifier.height(32.dp))

        UiMode.entries.forEach { uiMode ->
            Surface(
                onClick = { onUiModeClick(uiMode) },
                color = colorScheme.background,
                contentColor = colorScheme.onBackground
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiMode == data.uiMode,
                        onClick = null,
                        modifier = Modifier.padding(start = 16.dp),
                        colors = RadioButtonDefaults.colors(
                            unselectedColor = colorScheme.onBackground,
                            selectedColor = colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(32.dp))
                    Text(
                        text = stringResource(uiMode.label),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

private val UiMode.label: Int
    @StringRes get() = when (this) {
        UiMode.UseLightTheme -> R.string.settings_appearance_ui_mode_option_light
        UiMode.UseDarkTheme -> R.string.settings_appearance_ui_mode_option_dark
        UiMode.UseSystemSettings -> R.string.settings_appearance_ui_mode_option_system
    }

@CompactPreview
@Composable
private fun AppearanceScreen_Preview() = MatanePreview {
    Screen(
        state = AppearanceState(
            uiMode = UiMode.UseSystemSettings
        ),
        onUiModeClick = {},
        onNavigateUp = {}
    )
}
