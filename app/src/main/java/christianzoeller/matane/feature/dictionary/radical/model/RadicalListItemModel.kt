package christianzoeller.matane.feature.dictionary.radical.model

import christianzoeller.matane.data.dictionary.model.Radical

data class RadicalListItemModel(
    val radical: Radical,
    val isLoading: Boolean
)