package christianzoeller.matane.feature.dictionary.search.model

import christianzoeller.matane.data.dictionary.model.KanjiInContext
import christianzoeller.matane.data.dictionary.model.VocabularyDetail
import christianzoeller.matane.data.dictionary.model.VocabularyOverview
import christianzoeller.matane.data.dictionary.model.vocabulary.Gloss
import christianzoeller.matane.data.dictionary.model.vocabulary.KanjiElement
import christianzoeller.matane.data.dictionary.model.vocabulary.ReadingElement
import christianzoeller.matane.data.dictionary.model.vocabulary.Sense

object VocabularyMocks {
    val umiOverview = VocabularyOverview(
        id = 1201190,
        word = "海",
        readings = "うみ, み, わた, わだ",
        meanings = "sea, ocean, waters"
    )

    val kainankeihanOverview = VocabularyOverview(
        id = 2840027,
        word = "海南鶏飯",
        readings = "かいなんけいはん, ハイナンジーファン",
        meanings = "Hainanese chicken rice"
    )

    val mirukuigaiOverview = VocabularyOverview(
        id = 2604230,
        word = "海松食貝",
        otherVersions = "水松食貝",
        readings = "みるくいがい, ミルクイガイ",
        meanings = "mirugai clam (Tresus keenae, species of gaper clam)"
    )

    val kaijoujieikanOverview = VocabularyOverview(
        id = 2855517,
        word = "海上自衛官",
        readings = "かいじょうじえいかん",
        meanings = "member of the Maritime Self-Defense Force, MSDF official"
    )

    val tokaiOverview = VocabularyOverview(
        id = 1739680,
        word = "渡海",
        readings = "とかい",
        meanings = "crossing the sea"
    )

    val searchResults = listOf(
        umiOverview,
        kainankeihanOverview,
        mirukuigaiOverview,
        kaijoujieikanOverview,
        tokaiOverview
    )

    val umi = VocabularyDetail(
        id = 1201190,
        kanjiElements = listOf(
            KanjiElement(
                kanji = "海",
                priorities = listOf(
                    "ichi1",
                    "news1",
                    "nf02"
                )
            )
        ),
        readingElements = listOf(
            ReadingElement(
                reading = "うみ",
                priorities = listOf(
                    "ichi1",
                    "news1",
                    "nf02"
                )
            ),
            ReadingElement(
                reading = "み",
                info = listOf("out-dated or obsolete kana usage")
            ),
            ReadingElement(
                reading = "わた",
                info = listOf("out-dated or obsolete kana usage")
            ),
            ReadingElement(
                reading = "わだ",
                info = listOf("out-dated or obsolete kana usage")
            )
        ),
        senses = listOf(
            Sense(
                partOfSpeech = listOf("noun (common) (futsuumeishi)"),
                glosses = listOf(
                    Gloss(word = "sea"),
                    Gloss(word = "ocean"),
                    Gloss(word = "waters")
                )
            ),
            Sense(
                glosses = listOf(
                    Gloss(
                        language = "dut",
                        word = "zee"
                    ),
                    Gloss(
                        language = "dut",
                        word = "oceaan"
                    )
                )
            ),
            Sense(
                glosses = listOf(
                    Gloss(
                        language = "fre",
                        word = "mer"
                    ),
                    Gloss(
                        language = "fre",
                        word = "océan"
                    ),
                    Gloss(
                        language = "fre",
                        word = "eaux"
                    )
                )
            ),
            Sense(
                glosses = listOf(
                    Gloss(
                        language = "ger",
                        word = "Meer"
                    ),
                    Gloss(
                        language = "ger",
                        word = "See"
                    )
                )
            ),
            Sense(
                glosses = listOf(
                    Gloss(
                        language = "ger",
                        word = "See"
                    )
                )
            ),
            Sense(
                glosses = listOf(
                    Gloss(
                        language = "ger",
                        word = "etw., das wie ein Meer ist"
                    )
                )
            ),
            Sense(
                glosses = listOf(
                    Gloss(
                        language = "ger",
                        word = "eine große Menge gleicher Dinge"
                    )
                )
            ),
            Sense(
                glosses = listOf(
                    Gloss(
                        language = "ger",
                        word = "Vertiefung im Tuschstein"
                    )
                )
            ),
            Sense(
                glosses = listOf(
                    Gloss(
                        language = "hun",
                        word = "strand"
                    )
                )
            ),
            Sense(
                glosses = listOf(
                    Gloss(
                        language = "rus",
                        word = "море"
                    )
                )
            ),
            Sense(
                glosses = listOf(
                    Gloss(
                        language = "slv",
                        word = "morje"
                    )
                )
            ),
            Sense(
                glosses = listOf(
                    Gloss(
                        language = "spa",
                        word = "mar"
                    ),
                    Gloss(
                        language = "spa",
                        word = "playa"
                    )
                )
            ),
            Sense(
                glosses = listOf(
                    Gloss(
                        language = "swe",
                        word = "hav"
                    ),
                    Gloss(
                        language = "swe",
                        word = "strand"
                    )
                )
            )
        )
    )

    val kanjiInUmi = listOf(
        KanjiInContext(
            id = 28023,
            literal = "海",
            onyomi = "カイ",
            kunyomi = "うみ",
            meanings = "sea, ocean"
        )
    )
}