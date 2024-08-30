package christianzoeller.matane.data.dictionary.model.firestore.kanji

import christianzoeller.matane.data.dictionary.model.kanji.QueryCode
import christianzoeller.matane.data.dictionary.model.kanji.QueryCodeType
import christianzoeller.matane.data.dictionary.model.kanji.SkipMisclassificationType
import com.google.firebase.firestore.PropertyName

data class QueryCodeFirestoreDto(
    @PropertyName("Type")
    var type: String? = null,
    @PropertyName("Value")
    var value: String? = null,
    @PropertyName("SkipMisclassification")
    var skipMisclassification: String? = null
)

fun QueryCodeFirestoreDto.toQueryCode(): QueryCode {
    if (type != "skip") {
        require(skipMisclassification == null)
    }

    val queryCodeType = when (type) {
        "skip" -> QueryCodeType.Skip
        "sh_desc" -> QueryCodeType.SHDescriptor
        "four_corner" -> QueryCodeType.FourCorner
        "deroo" -> QueryCodeType.DeRoo
        "misclass" -> QueryCodeType.Misclassification
        else -> throw IllegalArgumentException("Illegal query code type: $type")
    }

    val queryCodeValue = value!! // This is supposed to throw

    val skipMisclassificationType = when (skipMisclassification) {
        "posn" -> SkipMisclassificationType.Position
        "stroke_count" -> SkipMisclassificationType.StrokeCount
        "stroke_and_posn" -> SkipMisclassificationType.StrokeCountAndPosition
        "stroke_diff" -> SkipMisclassificationType.StrokeDifference
        null -> null
        else -> throw IllegalArgumentException("Illegal skip misclassification type: $skipMisclassification")
    }

    return QueryCode(
        type = queryCodeType,
        value = queryCodeValue,
        skipMisclassification = skipMisclassificationType
    )
}
