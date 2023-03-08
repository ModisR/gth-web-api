package v1.gth.init

import org.scalacheck.{Gen, Shrink}
import v1.gth.BaseSpec
import v1.gth.parse.Parse

import scala.language.postfixOps

class ShermanConsonantSpec extends BaseSpec {
  "Parser" should {
    "invert asString method" in {
      ShermanConsonant.values foreach { consonant =>
        val string = consonant.asString
        val Parse.Success(_, result) = Parse[ShermanConsonant](string)

        consonant mustBe result
      }
    }

    "fail to parse a non-consonant" in {
      import Shrink.shrinkAny

      val invalidStrings = for {
        n <- Gen.chooseNum(1, 2)
        string <- Gen.listOfN(n, Gen.asciiPrintableChar) map (_.mkString.toUpperCase)
        if string.distinct diff ('A' to 'Z') nonEmpty
      } yield string

      forAll(invalidStrings) { invalidString =>
        val result = ShermanConsonant parse invalidString

        result match {
          case Parse.Success(remaining, _) => assert(remaining.nonEmpty)
          case _ =>
        }
      }
    }
  }
}
