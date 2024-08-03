package christianzoeller.matane.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object OssLicenses : Destination
}