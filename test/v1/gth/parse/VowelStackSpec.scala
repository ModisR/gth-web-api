package v1.gth.parse

import org.scalacheck.{Arbitrary, Gen, Shrink}
import v1.gth.BaseSpec
import v1.gth.init.ShermanVowel

import scala.language.postfixOps

class VowelStackSpec extends BaseSpec {
  "Parser" should {
    "successfully undo method toString" in {
      implicit val arbVowelStack: Arbitrary[VowelStack] =
        Arbitrary(
          for {
            head <- Gen oneOf ShermanVowel.values
            tail <- Gen listOf (Gen oneOf ShermanVowel.table(head.base).keys)
          } yield VowelStack(head, tail)
        )

      forAll { vowelStack: VowelStack =>
        val string = vowelStack.toString
        val Parse.Success(remaining, result) = VowelStack parse string

        remaining mustBe empty
        result mustBe vowelStack
      }
    }

    "fail on strings containing no consonants" in {
      import Shrink.shrinkAny

      val invalidChars = Gen.asciiPrintableChar filterNot ("AEIOU" contains _)
      val invalidStrings = Gen listOf invalidChars map (_.mkString)

      forAll(invalidStrings) { invalidString =>
        val result = VowelStack parse invalidString

        assert(!result.isSuccess)
      }
    }
  }
}
