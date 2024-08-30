package christianzoeller.matane.data.dictionary.model.kanji

data class QueryCode(
    val type: QueryCodeType,
    val value: String,
    val skipMisclassification: SkipMisclassificationType? = null
) {
    init {
        if (type != QueryCodeType.Skip) {
            require(skipMisclassification == null)
        }
    }
}

enum class QueryCodeType {
    Skip,
    SHDescriptor,
    FourCorner,
    DeRoo,
    Misclassification
}

enum class SkipMisclassificationType {
    Position,
    StrokeCount,
    StrokeCountAndPosition,
    StrokeDifference
}