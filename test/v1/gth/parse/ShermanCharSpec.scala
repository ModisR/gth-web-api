package v1.gth.parse

import org.scalacheck.Gen
import v1.gth.BaseSpec

class ShermanCharSpec extends BaseSpec {
  "Parser" should {
    "successfully undo method toString" in {
      forAll { shermanChar: ShermanChar =>
        val string = shermanChar.asString
        val Parse.Success(remaining, result) = Parse[ShermanChar](string)

        remaining mustBe empty
        result mustBe shermanChar
      }
    }

    "fail on strings containing no consonants" in {
      import org.scalacheck.Shrink.shrinkAny

      val validChars = '\'' +: '-' +: ('A' to 'Z')
      val invalidChars = Gen.asciiPrintableChar filterNot validChars.contains
      val invalidStrings = Gen listOf invalidChars map (_.mkString)

      forAll(invalidStrings) { invalidString =>
        val result = Parse[ShermanChar](invalidString)

        assert(!result.isSuccess)
      }
    }
  }
}
