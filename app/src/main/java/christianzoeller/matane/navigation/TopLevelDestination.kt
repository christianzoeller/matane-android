package christianzoeller.matane.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import christianzoeller.matane.R
import kotlinx.serialization.Serializable

sealed class TopLevelDestination(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    @StringRes val iconDescription: Int
) {
    @Serializable
    data object Dictionary : TopLevelDestination(
        label = R.string.dictionary_tab_label,
        icon = R.drawable.baseline_search_24,
        iconDescription = R.string.dictionary_tab_icon_description
    )

    @Serializable
    data object Settings : TopLevelDestination(
        label = R.string.settings_tab_label,
        icon = R.drawable.baseline_settings_24,
        iconDescription = R.string.settings_tab_icon_description
    )


    // Convenience constructor to be used by kotlinx.serialization
    @Suppress("unused")
    private constructor() : this(
        label = 0,
        icon = 0,
        iconDescription = 0
    )
}