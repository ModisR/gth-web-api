package v1.gth.parse

import org.scalacheck.{Gen, Shrink}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks.forAll

import scala.language.postfixOps

class ShermanParserSpec extends PlaySpec {
  "Sherman parser" should {
    val letterTables = new LetterTables
    val parser = new ShermanParser(letterTables)

    "be the dual of ShermanWord.asString" in {
      val validChars = ('A' to 'Z') ++ "'-"

      implicit val shrinkShermanText: Shrink[String] =
        Shrink { text =>
          val validStrings = Shrink.shrinkString shrink text filter (_ diff validChars isEmpty)
          if (validStrings.nonEmpty) {
            println("Shrinking text " + text)
            println("down to " + (validStrings mkString ",") + ".")
          }
          validStrings
        }

      val validShermanWords = Gen nonEmptyContainerOf[Array, Char] (Gen oneOf validChars) map (_.mkString)

      forAll(validShermanWords) { validShermanWord =>
        val ParserSuccess(_, result) = parser.word parse validShermanWord
        println(validShermanWord)
        validShermanWord mustBe result.asString
      }(implicitly, shrinkShermanText, implicitly, implicitly, implicitly)
    }
    "correctly pass ShermanChars representing 2 letters" in {
      import letterTables.consonants2

      val doubleConsonants = Gen oneOf consonants2

      forAll(doubleConsonants) { shermanConsonant =>
        val ParserSuccess(_, ShermanWord(List(result))) = parser.word parse shermanConsonant.toString
        println(shermanConsonant)
        shermanConsonant mustBe result
      }
    }
  }
}
