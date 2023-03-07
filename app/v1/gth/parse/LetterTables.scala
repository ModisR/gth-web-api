package v1.gth.parse

import v1.gth.parse.ShermanChar._

object LetterTables {
  private val vowelTable = Map(
    VowelTop -> Map(
      NoDeco -> "O"
    ),
    VowelMiddle -> Map(
      NoDeco -> "E",
      LineUp -> "I",
      LineDown -> "U"
    ),
    VowelBottom -> Map(
      NoDeco -> "A"
    )
  )

  val vowels: Iterable[Vowel] = for {
    (base, row) <- vowelTable
    (deco, str) <- row
  } yield new Vowel(base, deco, str)


  private val consonantTable = Map(
    ArcMajor -> Map(
      ConsonantDots(0) -> "B",
      ConsonantDots(2) -> "CH",
      ConsonantDots(3) -> "D",
      ConsonantLines(1) -> "G",
      ConsonantLines(2) -> "H",
      ConsonantLines(3) -> "F"
    ),
    CircleIn -> Map(
      ConsonantDots(0) -> "J",
      ConsonantDots(1) -> "PH",
      ConsonantDots(2) -> "K",
      ConsonantDots(3) -> "L",
      ConsonantDots(4) -> "C",
      ConsonantLines(1) -> "N",
      ConsonantLines(2) -> "P",
      ConsonantLines(3) -> "M"
    ),
    ArcMinor -> Map(
      ConsonantDots(0) -> "T",
      ConsonantDots(1) -> "WH",
      ConsonantDots(2) -> "SH",
      ConsonantDots(3) -> "R",
      ConsonantLines(1) -> "V",
      ConsonantLines(2) -> "W",
      ConsonantLines(3) -> "S"
    ),
    CircleOut -> Map(
      ConsonantDots(0) -> "TH",
      ConsonantDots(1) -> "GH",
      ConsonantDots(2) -> "Y",
      ConsonantDots(3) -> "Z",
      ConsonantDots(4) -> "Q",
      ConsonantLines(1) -> "QU",
      ConsonantLines(2) -> "X",
      ConsonantLines(3) -> "NG"
    )
  )

  private val consonants = for {
    (base, row) <- consonantTable
    (deco, str) <- row
  } yield new Consonant(base, deco, str)

  val (consonants2, consonants1) = consonants partition (_.toString.length > 1)
}
