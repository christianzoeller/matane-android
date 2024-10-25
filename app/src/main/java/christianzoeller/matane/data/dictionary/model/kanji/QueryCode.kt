package christianzoeller.matane.data.dictionary.model.kanji

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QueryCode(
    @SerialName("Type")
    val type: QueryCodeType,
    @SerialName("Value")
    val value: String,
    @SerialName("SkipMisclassification")
    val skipMisclassification: SkipMisclassificationType? = null
)

@Serializable
enum class QueryCodeType {
    @SerialName("skip")
    Skip,
    @SerialName("sh_desc")
    SHDescriptor,
    @SerialName("four_corner")
    FourCorner,
    @SerialName("deroo")
    DeRoo,
    @SerialName("misclass")
    Misclassification
}

@Serializable
enum class SkipMisclassificationType {
    @SerialName("posn")
    Position,
    @SerialName("stroke_count")
    StrokeCount,
    @SerialName("stroke_and_posn")
    StrokeCountAndPosition,
    @SerialName("stroke_diff")
    StrokeDifference
}