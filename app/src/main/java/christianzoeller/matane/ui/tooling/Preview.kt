package christianzoeller.matane.ui.tooling

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

private const val LightModePreview = "Light"
private const val DarkModePreview = "Dark"

private const val LightModePreviewGroup = "Light"
private const val DarkModePreviewGroup = "Dark"

const val LightModePreviewBackground = 0xFFFFFFFF
const val DarkModePreviewBackground = 0x0000000000L

@Preview(
    name = LightModePreview,
    group = LightModePreviewGroup,
    showBackground = true,
    backgroundColor = LightModePreviewBackground,
    widthDp = 480,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = DarkModePreview,
    group = DarkModePreviewGroup,
    showBackground = true,
    backgroundColor = DarkModePreviewBackground,
    widthDp = 480,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class CompactPreview

@Preview(
    name = LightModePreview,
    group = LightModePreviewGroup,
    showBackground = true,
    backgroundColor = LightModePreviewBackground,
    widthDp = 720,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = DarkModePreview,
    group = DarkModePreviewGroup,
    showBackground = true,
    backgroundColor = DarkModePreviewBackground,
    widthDp = 720,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class MediumPreview

@Preview(
    name = LightModePreview,
    group = LightModePreviewGroup,
    showBackground = true,
    backgroundColor = LightModePreviewBackground,
    widthDp = 960,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = DarkModePreview,
    group = DarkModePreviewGroup,
    showBackground = true,
    backgroundColor = DarkModePreviewBackground,
    widthDp = 960,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class ExpandedPreview