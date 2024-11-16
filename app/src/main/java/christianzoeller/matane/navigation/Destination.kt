package christianzoeller.matane.navigation

import christianzoeller.matane.feature.dictionary.kanji.KanjiListType
import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Search : Destination

    @Serializable
    data class Kanji(
        val listType: KanjiListType
    ) : Destination

    @Serializable
    data object Radical : Destination

    @Serializable
    data object Settings : Destination

    @Serializable
    data object AppearanceSettings : Destination

    @Serializable
    data object Acknowledgements : Destination

    @Serializable
    data object OssLicenses : Destination
}