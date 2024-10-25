package christianzoeller.matane.data.dictionary.model.kanji

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DictionaryReference(
    @SerialName("Index")
    val index: String,
    @SerialName("Type")
    val type: DictionaryType,
    @SerialName("MoroVolume")
    val moroVolume: Int? = null,
    @SerialName("MoroPage")
    val moroPage: Int? = null
)

@Serializable
enum class DictionaryType {
    @SerialName("nelson_c")
    ClassicNelson,
    @SerialName("nelson_n")
    NewNelson,
    @SerialName("halpern_njecd")
    HalpernNjecd,
    @SerialName("halpern_kkd")
    HalpernKkd,
    @SerialName("halpern_kkld")
    HalpernKkld,
    @SerialName("halpern_kkld_2ed")
    HalpernKkld2,
    @SerialName("heisig")
    Heisig,
    @SerialName("heisig6")
    Heisig6,
    @SerialName("gakken")
    Gakken,
    @SerialName("oneill_names")
    ONeillNames,
    @SerialName("oneill_kk")
    ONeillKK,
    @SerialName("moro")
    Moro,
    @SerialName("henshall")
    Henshall,
    @SerialName("sh_kk")
    ShKk,
    @SerialName("sh_kk2")
    ShKk2,
    @SerialName("sakade")
    Sakade,
    @SerialName("jf_cards")
    JFCards,
    @SerialName("henshall3")
    Henshall3,
    @SerialName("tutt_cards")
    TuttCards,
    @SerialName("crowley")
    Crowley,
    @SerialName("kanji_in_context")
    KanjiInContext,
    @SerialName("busy_people")
    BusyPeople,
    @SerialName("kodansha_compact")
    KodanshaCompact,
    @SerialName("maniette")
    Maniette
}
