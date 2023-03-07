package v1.gth.init

import v1.gth.BaseSpec
import v1.gth.parse.Parse
import org.scalacheck.{Gen, Shrink}

import scala.language.postfixOps

class ShermanVowelSpec extends BaseSpec {
  "Parser" should {
    "invert asString method" in {
      ShermanVowel.values foreach { vowel =>
        val string = vowel.toString
        val Parse.Success(_, result) = ShermanVowel parse string

        vowel mustBe result
      }
    }

    "fail to parse a non-vowel" in {
      import Shrink.shrinkAny

      val invalidStrings = Gen.asciiPrintableChar map (_.toString.toUpperCase) filterNot "AEIOU".contains

      forAll(invalidStrings) { invalidString =>
        val result = ShermanVowel parse invalidString

        result match {
          case Parse.Success(remaining, _) => assert(remaining.nonEmpty)
          case _ =>
        }
      }
    }
  }
}
