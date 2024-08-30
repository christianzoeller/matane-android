package christianzoeller.matane.data.dictionary.model.firestore.kanji

import christianzoeller.matane.data.dictionary.model.kanji.DictionaryReference
import christianzoeller.matane.data.dictionary.model.kanji.DictionaryType
import com.google.firebase.firestore.PropertyName

data class DictionaryReferenceFirestoreDto(
    @PropertyName("Index")
    var index: String? = null,
    @PropertyName("Type")
    var type: String? = null,
    @PropertyName("MoroVolume")
    var moroVolume: Int? = null,
    @PropertyName("MoroPage")
    var moroPage: Int? = null
)

fun DictionaryReferenceFirestoreDto.toDictionaryReference(): DictionaryReference {
    if (type != "moro") {
        require(moroVolume == null)
        require(moroPage == null)
    }

    val dictionaryReferenceIndex = index!! // This is supposed to throw

    val dictionaryReferenceType = when (type) {
        "nelson_c" -> DictionaryType.ClassicNelson
        "nelson_n" -> DictionaryType.NewNelson
        "halpern_njecd" -> DictionaryType.HalpernNjecd
        "halpern_kkd" -> DictionaryType.HalpernKkd
        "halpern_kkld" -> DictionaryType.HalpernKkld
        "halpern_kkld_2ed" -> DictionaryType.HalpernKkld2
        "heisig" -> DictionaryType.Heisig
        "heisig6" -> DictionaryType.Heisig6
        "gakken" -> DictionaryType.Gakken
        "oneill_names" -> DictionaryType.ONeillNames
        "oneill_kk" -> DictionaryType.ONeillKK
        "moro" -> DictionaryType.Moro
        "henshall" -> DictionaryType.Henshall
        "sh_kk" -> DictionaryType.ShKk
        "sh_kk2" -> DictionaryType.ShKk2
        "sakade" -> DictionaryType.Sakade
        "jf_cards" -> DictionaryType.JFCards
        "henshall3" -> DictionaryType.Henshall3
        "tutt_cards" -> DictionaryType.TuttCards
        "crowley" -> DictionaryType.Crowley
        "kanji_in_context" -> DictionaryType.KanjiInContext
        "busy_people" -> DictionaryType.BusyPeople
        "kodansha_compact" -> DictionaryType.KodanshaCompact
        "maniette" -> DictionaryType.Maniette
        else -> throw IllegalArgumentException("Illegal dictionary reference type: $type")
    }

    return DictionaryReference(
        index = dictionaryReferenceIndex,
        type = dictionaryReferenceType,
        moroVolume = moroVolume,
        moroPage = moroPage
    )
}

