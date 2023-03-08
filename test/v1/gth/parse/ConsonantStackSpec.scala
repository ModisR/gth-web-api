package v1.gth.parse

import org.scalacheck.{Gen, Shrink}
import v1.gth.BaseSpec

import scala.language.postfixOps

class ConsonantStackSpec extends BaseSpec {
  "Parser" should {
    "successfully undo method toString" in {
      forAll { consonantStack: ConsonantStack =>
        val string = consonantStack.asString
        val Parse.Success(remaining, result) = Parse[ConsonantStack](string)

        remaining mustBe empty
        result mustBe consonantStack
      }
    }

    "fail on strings containing no consonants" in {
      import Shrink.shrinkAny

      val consonants = 'B' to 'Z' diff "EIOU"
      val invalidChars = Gen.asciiPrintableChar filterNot consonants.contains
      val invalidStrings = Gen listOf invalidChars map (_.mkString)

      forAll(invalidStrings) { invalidString =>
        val result = Parse[ConsonantStack](invalidString)

        assert(!result.isSuccess)
      }
    }
  }
}
