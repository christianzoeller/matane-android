package christianzoeller.matane.feature.dictionary.kanji.model

import christianzoeller.matane.data.dictionary.model.KanjiInContext

data class KanjiListItemModel(
    val kanji: KanjiInContext,
    val isLoading: Boolean
)