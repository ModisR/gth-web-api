package v1.gth.parse

import org.scalacheck.{Arbitrary, Gen, Shrink}
import v1.gth.BaseSpec
import v1.gth.init.ShermanConsonant

import scala.language.postfixOps

class ConsonantStackSpec extends BaseSpec {
  "Parser" should {
    "successfully undo method toString" in {
      implicit val arbConsonantStack: Arbitrary[ConsonantStack] =
        Arbitrary(
          for {
            head <- Gen oneOf ShermanConsonant.values
            tail <- Gen listOf (Gen oneOf ShermanConsonant.table(head.base).keys)
          } yield ConsonantStack(head, tail)
        )

      forAll { consonantStack: ConsonantStack =>
        val string = consonantStack.toString
        val Parse.Success(remaining, result) = ConsonantStack parse string

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
        val result = ConsonantStack parse invalidString

        assert(!result.isSuccess)
      }
    }
  }
}
