package christianzoeller.matane.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = DarkTealGray,
    onBackground = SoftWhite,
    surface = DarkTealGray,
    onSurface = MutedGray,
    surfaceContainer = DarkRichTealNavy,
    surfaceVariant = VeryDarkTeal,
    onSurfaceVariant = SoftWhite,
    primary = TealGrayAccent,
    primaryContainer = CoolTealGray,
    secondary = TealGreen,
    onSecondary = SoftWhite,
    secondaryContainer = MutedDarkTealGray,
    onSecondaryContainer = SoftWhite,
    tertiaryContainer = TealGray,
    scrim = SemiTransparentDarkTeal
)

private val LightColorScheme = lightColorScheme(
    background = GrayishWhite,
    onBackground = CharcoalGray,
    surface = GrayishWhite,
    onSurface = SlateGray,
    surfaceContainer = DeepNavyBlue,
    surfaceVariant = SoftTeal,
    onSurfaceVariant = FullWhite,
    primary = DeepNavyBlue,
    primaryContainer = SkyBlue,
    secondary = TealGreen,
    onSecondary = FullWhite,
    secondaryContainer = DeepTealGreen,
    onSecondaryContainer = FullWhite,
    tertiaryContainer = SoftTeal,
    scrim = TranslucentNavyBlue
)

@Composable
fun MataneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}