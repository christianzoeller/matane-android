package christianzoeller.matane.data.dictionary.model.firestore.kanji

import christianzoeller.matane.data.dictionary.model.kanji.LegacyJlptLevel
import christianzoeller.matane.data.dictionary.model.kanji.Misc
import christianzoeller.matane.data.dictionary.model.kanji.Variant
import christianzoeller.matane.data.dictionary.model.kanji.VariantType
import com.google.firebase.firestore.PropertyName

data class MiscFirestoreDto(
    @PropertyName("Grade")
    var grade: Int? = null,
    @PropertyName("StrokeCounts")
    var strokeCounts: List<Int>? = null,
    @PropertyName("Variants")
    var variants: List<VariantFirestoreDto>? = null,
    @PropertyName("Frequency")
    var frequency: Int? = null,
    @PropertyName("RadicalNames")
    var radicalNames: List<String>? = null,
    @PropertyName("LegacyJlptLevel")
    var legacyJlptLevel: Int? = null,
    @PropertyName("XmlName")
    var xmlName: Any? = null
)

fun MiscFirestoreDto.toMisc(): Misc {
    require(grade == null || grade in 1..6 || grade in 8..10)
    require(frequency == null || frequency in 1..2501) // 2501 is NOT a typo

    val strokeCount = strokeCounts?.first()!! // This is supposed to throw

    return Misc(
        grade = grade,
        strokeCount = strokeCount,
        variants = variants?.map { it.toVariant() },
        frequency = frequency,
        radicalNames = radicalNames,
        legacyJlptLevel = legacyJlptLevel?.let {
            LegacyJlptLevel.entries[it - 1]
        }
    )
}

data class VariantFirestoreDto(
    @PropertyName("Type")
    var type: String? = null,
    @PropertyName("Value")
    var value: String? = null
)

fun VariantFirestoreDto.toVariant(): Variant {
    val variantType = when (type) {
        "jis208" -> VariantType.Jis208
        "jis212" -> VariantType.Jis212
        "jis213" -> VariantType.Jis213
        "deroo" -> VariantType.DeRoo
        "njecd" -> VariantType.Njecd
        "s_h" -> VariantType.SH
        "nelson_c" -> VariantType.ClassicNelson
        "ONeill" -> VariantType.ONeill
        "ucs" -> VariantType.Ucs
        else -> throw IllegalArgumentException("Illegal variant type: $type")
    }

    val variantValue = value!! // This is supposed to throw

    return Variant(
        type = variantType,
        value = variantValue
    )
}
