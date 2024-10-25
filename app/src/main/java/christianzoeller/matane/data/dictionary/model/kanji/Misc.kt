package christianzoeller.matane.data.dictionary.model.kanji

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class Misc(
    @SerialName("Grade")
    val grade: Int? = null,
    @SerialName("StrokeCounts")
    val strokeCount: List<Int>,
    @SerialName("Variants")
    val variants: List<Variant>? = null,
    @SerialName("Frequency")
    val frequency: Int? = null,
    @SerialName("RadicalNames")
    val radicalNames: List<String>? = null,
    @SerialName("LegacyJlptLevel")
    val legacyJlptLevel: LegacyJlptLevel? = null
) {
    val isKyouiku get() = grade in 1..6
    val isJouyou get() = isKyouiku || grade == 8
    val isJinmeiyou get() = grade == 9 || grade == 10
    val taughtInGrade get() = grade?.takeUnless { it > 6 }
}

@Serializable
data class Variant(
    @SerialName("Type")
    val type: VariantType,
    @SerialName("Value")
    val value: String
)

@Serializable
enum class VariantType {
    @SerialName("jis208")
    Jis208,
    @SerialName("jis212")
    Jis212,
    @SerialName("jis213")
    Jis213,
    @SerialName("deroo")
    DeRoo,
    @SerialName("njecd")
    Njecd,
    @SerialName("s_h")
    SH,
    @SerialName("nelson_c")
    ClassicNelson,
    @SerialName("ONeill")
    ONeill,
    @SerialName("ucs")
    Ucs
}

@Serializable(with = LegacyJlptLevelSerializer::class)
enum class LegacyJlptLevel {
    N1,
    N2,
    N3,
    N4
}

private class LegacyJlptLevelSerializer : KSerializer<LegacyJlptLevel> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = this::class.qualifiedName!!,
        kind = PrimitiveKind.INT
    )

    override fun deserialize(decoder: Decoder): LegacyJlptLevel {
        val level = decoder.decodeInt()
        return LegacyJlptLevel.entries[level - 1]
    }

    override fun serialize(encoder: Encoder, value: LegacyJlptLevel) {
        encoder.encodeInt(value.ordinal + 1)
    }
}