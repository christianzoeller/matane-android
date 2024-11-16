package christianzoeller.matane.feature.settings.appearance

import christianzoeller.matane.data.settings.model.UiMode

data class AppearanceState(
    val uiMode: UiMode = UiMode.UseSystemSettings
)