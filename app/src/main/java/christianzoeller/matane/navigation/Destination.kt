package christianzoeller.matane.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Search : Destination

    @Serializable
    data object Settings : Destination

    @Serializable
    data object OssLicenses : Destination
}