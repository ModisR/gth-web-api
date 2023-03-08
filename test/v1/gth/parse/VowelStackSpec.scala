package v1.gth.parse

import org.scalacheck.{Gen, Shrink}
import v1.gth.BaseSpec

import scala.language.postfixOps

class VowelStackSpec extends BaseSpec {
  "Parser" should {
    "successfully undo method toString" in {
      forAll { vowelStack: VowelStack =>
        val string = vowelStack.asString
        val Parse.Success(remaining, result) = Parse[VowelStack](string)

        remaining mustBe empty
        result mustBe vowelStack
      }
    }

    "fail on strings containing no consonants" in {
      import Shrink.shrinkAny

      val invalidChars = Gen.asciiPrintableChar filterNot ("AEIOU" contains _)
      val invalidStrings = Gen listOf invalidChars map (_.mkString)

      forAll(invalidStrings) { invalidString =>
        val result = Parse[VowelStack](invalidString)

        assert(!result.isSuccess)
      }
    }
  }
}
