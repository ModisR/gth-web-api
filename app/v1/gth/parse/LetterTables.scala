package v1.gth.parse

import javax.inject.{Inject, Singleton}

@Singleton
class LetterTables @Inject()() {

  val vowelTable: Map[VowelBase, Map[VowelDeco, String]] = Map(
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

  val vowels: Iterable[ShermanVowel] = for {
    (base, row) <- vowelTable
    (deco, str) <- row
  } yield new ShermanVowel(base, deco, str)


  val consonantTable: Map[ConsonantBase, Map[ConsonantDeco, String]] = Map(
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

  val consonants: Iterable[ShermanConsonant] = for {
    (base, row) <- consonantTable
    (deco, str) <- row
  } yield new ShermanConsonant(base, deco, str)

  val (consonants2, consonants1) = consonants partition (_.toString.length > 1)
}
