package christianzoeller.matane.feature.dictionary.kanji.model

import christianzoeller.matane.data.dictionary.model.CodePoint
import christianzoeller.matane.data.dictionary.model.CodePointType
import christianzoeller.matane.data.dictionary.model.Kanji
import christianzoeller.matane.data.dictionary.model.Radical
import christianzoeller.matane.data.dictionary.model.RadicalType
import christianzoeller.matane.data.dictionary.model.kanji.DictionaryReference
import christianzoeller.matane.data.dictionary.model.kanji.DictionaryType
import christianzoeller.matane.data.dictionary.model.kanji.LegacyJlptLevel
import christianzoeller.matane.data.dictionary.model.kanji.Meaning
import christianzoeller.matane.data.dictionary.model.kanji.Misc
import christianzoeller.matane.data.dictionary.model.kanji.QueryCode
import christianzoeller.matane.data.dictionary.model.kanji.QueryCodeType
import christianzoeller.matane.data.dictionary.model.kanji.Reading
import christianzoeller.matane.data.dictionary.model.kanji.ReadingMeaning
import christianzoeller.matane.data.dictionary.model.kanji.ReadingMeaningGroup
import christianzoeller.matane.data.dictionary.model.kanji.ReadingType

object KanjiMocks {
    val sortOfThing = Kanji(
        id = 28982,
        literal = "然",
        codePoints = listOf(
            CodePoint(type = CodePointType.Ucs, value = "7136"),
            CodePoint(type = CodePointType.Jis208, value = "1-33-19"),
        ),
        radicals = listOf(
            Radical(type = RadicalType.Classical, value = 86),
        ),
        misc = Misc(
            grade = 4,
            strokeCount = listOf(12),
            frequency = 401,
            legacyJlptLevel = LegacyJlptLevel.N2,
        ),
        dictionaryReferences = listOf(
            DictionaryReference(index = "2770", type = DictionaryType.ClassicNelson),
            DictionaryReference(index = "3435", type = DictionaryType.NewNelson),
            DictionaryReference(index = "2782", type = DictionaryType.HalpernNjecd),
            DictionaryReference(index = "3456", type = DictionaryType.HalpernKkd),
            DictionaryReference(index = "1779", type = DictionaryType.HalpernKkld),
            DictionaryReference(index = "2423", type = DictionaryType.HalpernKkld2),
            DictionaryReference(index = "241", type = DictionaryType.Heisig),
            DictionaryReference(index = "256", type = DictionaryType.Heisig6),
            DictionaryReference(index = "375", type = DictionaryType.Gakken),
            DictionaryReference(index = "1788", type = DictionaryType.ONeillNames),
            DictionaryReference(index = "437", type = DictionaryType.ONeillKK),
            DictionaryReference(index = "19149", type = DictionaryType.Moro, moroVolume = 7, moroPage = 462),
            DictionaryReference(index = "528", type = DictionaryType.Henshall),
            DictionaryReference(index = "651", type = DictionaryType.ShKk),
            DictionaryReference(index = "662", type = DictionaryType.ShKk2),
            DictionaryReference(index = "450", type = DictionaryType.Sakade),
            DictionaryReference(index = "716", type = DictionaryType.JFCards),
            DictionaryReference(index = "557", type = DictionaryType.Henshall3),
            DictionaryReference(index = "592", type = DictionaryType.TuttCards),
            DictionaryReference(index = "144", type = DictionaryType.Crowley),
            DictionaryReference(index = "401", type = DictionaryType.KanjiInContext),
            DictionaryReference(index = "1279", type = DictionaryType.KodanshaCompact),
            DictionaryReference(index = "246", type = DictionaryType.Maniette),
        ),
        queryCodes = listOf(
            QueryCode(type = QueryCodeType.Skip, value = "2-8-4"),
            QueryCode(type = QueryCodeType.SHDescriptor, value = "4d8.10"),
            QueryCode(type = QueryCodeType.FourCorner, value = "2333.3"),
            QueryCode(type = QueryCodeType.DeRoo, value = "2540"),
        ),
        readingMeaning = ReadingMeaning(
            groups = listOf(
                ReadingMeaningGroup(
                    readings = listOf(
                        Reading(type = ReadingType.PinYin, value = "ran2"),
                        Reading(type = ReadingType.KoreanRomanized, value = "yeon"),
                        Reading(type = ReadingType.KoreanHangul, value = "연"),
                        Reading(type = ReadingType.Vietnam, value = "Nhiên"),
                        Reading(type = ReadingType.Onyomi, value = "ゼン"),
                        Reading(type = ReadingType.Onyomi, value = "ネン"),
                        Reading(type = ReadingType.Kunyomi, value = "しか"),
                        Reading(type = ReadingType.Kunyomi, value = "しか.り"),
                        Reading(type = ReadingType.Kunyomi, value = "しか.し"),
                        Reading(type = ReadingType.Kunyomi, value = "さ"),
                    ),
                    meanings = listOf(
                        Meaning(value = "sort of thing"),
                        Meaning(value = "so"),
                        Meaning(value = "if so"),
                        Meaning(value = "in that case"),
                        Meaning(value = "well"),
                        Meaning(value = "un certain", languageCode = "fr"),
                        Meaning(value = "si c'est comme ça", languageCode = "fr"),
                        Meaning(value = "dans ce cas", languageCode = "fr"),
                        Meaning(value = "suffixe adverbial", languageCode = "fr"),
                        Meaning(value = "de esa forma", languageCode = "es"),
                        Meaning(value = "en ese caso", languageCode = "es"),
                        Meaning(value = "Tipos de objetos", languageCode = "pt"),
                        Meaning(value = "assim", languageCode = "pt"),
                        Meaning(value = "se assim", languageCode = "pt"),
                        Meaning(value = "naquele caso", languageCode = "pt"),
                        Meaning(value = "bem", languageCode = "pt"),
                    )
                )
            )
        )
    )
}