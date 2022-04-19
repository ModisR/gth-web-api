package v1.gth.parse

import javax.inject.{Inject, Singleton}
import scala.language.postfixOps

@Singleton
class ShermanParser @Inject()(letterTables: LetterTables) {
  def parse(text: String): Iterable[ShermanWord] = text.toUpperCase.trim split "\\s+" map word.parse collect {
    case ParserSuccess(_, result) => result
  }

  private val consonant2: Parses[ShermanConsonant] = Parses oneOf letterTables.consonants2
  private val consonant1: Parses[ShermanConsonant] = Parses oneOf letterTables.consonants1

  private val vowel: Parses[ShermanVowel] = Parses oneOf letterTables.vowels
  private val nonLetter: Parses[ShermanChar] = Parses oneOf Seq(ShermanApostrophe, ShermanHyphen)

  private val char: Parses[ShermanChar] = consonant2 orElse consonant1 orElse vowel orElse nonLetter
  val word: Parses[ShermanWord] = Parses many char map ShermanWord.apply
}