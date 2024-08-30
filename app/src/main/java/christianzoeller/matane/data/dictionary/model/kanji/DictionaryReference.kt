package christianzoeller.matane.data.dictionary.model.kanji

data class DictionaryReference(
    val index: String,
    val type: DictionaryType,
    val moroVolume: Int? = null,
    val moroPage: Int? = null
) {
    init {
        if (type != DictionaryType.Moro) {
            require(moroVolume == null)
            require(moroPage == null)
        }
    }
}

enum class DictionaryType {
    ClassicNelson,
    NewNelson,
    HalpernNjecd,
    HalpernKkd,
    HalpernKkld,
    HalpernKkld2,
    Heisig,
    Heisig6,
    Gakken,
    ONeillNames,
    ONeillKK,
    Moro,
    Henshall,
    ShKk,
    ShKk2,
    Sakade,
    JFCards,
    Henshall3,
    TuttCards,
    Crowley,
    KanjiInContext,
    BusyPeople,
    KodanshaCompact,
    Maniette
}
