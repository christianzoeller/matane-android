package christianzoeller.matane.data.dictionary.model.kanji

data class Misc(
    val grade: Int? = null,
    val strokeCount: Int,
    val variants: List<Variant>? = null,
    val frequency: Int? = null,
    val radicalNames: List<String>? = null,
    val legacyJlptLevel: LegacyJlptLevel? = null
) {
    val isKyouiku get() = grade in 1..6
    val isJouyou get() = isKyouiku || grade == 8
    val isJinmeiyou get() = grade == 9 || grade == 10
    val taughtInGrade get() = grade?.takeUnless { it > 6 }
}

data class Variant(
    val type: VariantType,
    val value: String
)

enum class VariantType {
    Jis208,
    Jis212,
    Jis213,
    DeRoo,
    Njecd,
    SH,
    ClassicNelson,
    ONeill,
    Ucs
}

enum class LegacyJlptLevel {
    N1,
    N2,
    N3,
    N4
}
