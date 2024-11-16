package christianzoeller.matane.data.settings.repository

import android.content.Context
import android.content.res.Configuration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import christianzoeller.matane.data.settings.model.UiMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

private const val uiModeKey = "uiMode"

@Singleton
class PreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val uiMode = intPreferencesKey(uiModeKey)

    val uiModeFlow: Flow<UiMode> = context.dataStore.data
        .map { preferences ->
            val value = preferences[uiMode] ?: Configuration.UI_MODE_NIGHT_UNDEFINED
            UiMode.fromConfigurationValue(value)
        }

    suspend fun changeUiModePreference(newMode: UiMode) {
        context.dataStore.edit { preferences ->
            preferences[uiMode] = newMode.toConfigurationValue()
        }
    }
}

private fun UiMode.toConfigurationValue() =
    when (this) {
        UiMode.UseLightTheme -> Configuration.UI_MODE_NIGHT_NO
        UiMode.UseDarkTheme -> Configuration.UI_MODE_NIGHT_YES
        UiMode.UseSystemSettings -> Configuration.UI_MODE_NIGHT_UNDEFINED
    }

private fun UiMode.Companion.fromConfigurationValue(value: Int) =
    when (value) {
        Configuration.UI_MODE_NIGHT_NO -> UiMode.UseLightTheme
        Configuration.UI_MODE_NIGHT_YES -> UiMode.UseDarkTheme
        Configuration.UI_MODE_NIGHT_UNDEFINED -> UiMode.UseSystemSettings
        else -> throw IllegalArgumentException("Invalid value for UI mode: $value")
    }
