package v1.gth.parse

import org.scalacheck.{Gen, Shrink}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import scala.language.postfixOps

class ShermanWordSpec extends PlaySpec with ScalaCheckPropertyChecks {
  override implicit val generatorDrivenConfig: PropertyCheckConfiguration = PropertyCheckConfiguration(minSuccessful = 50)

  "Sherman parser" should {
    val validChars = ('A' to 'Z') ++ "'-"

    "be the dual of ShermanWord.asString" in {
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
        val Parse.Success(_, result) = ShermanWord parse validShermanWord
        println(validShermanWord)
        validShermanWord mustBe result.asString
      }
    }

    "correctly parse ShermanChars representing 2 letters" in {
      val doubleConsonants = Gen oneOf LetterTables.consonants2

      forAll(doubleConsonants) { shermanConsonant =>
        val Parse.Success(_, ShermanWord(List(result))) = ShermanWord parse shermanConsonant.toString
        println(shermanConsonant)
        shermanConsonant mustBe result
      }
    }

    "not completely parse a string containing invalid chars" in {
      import Shrink.shrinkAny

      val invalidStrings = Gen.asciiPrintableStr map (_.toUpperCase) filter (_.distinct diff validChars nonEmpty)

      forAll(invalidStrings){ invalidString =>
        val result = ShermanWord parse invalidString

        result match {
          case Parse.Success(remaining, _) => assert(remaining.nonEmpty)
          case _ =>
        }
      }
    }
  }
}
