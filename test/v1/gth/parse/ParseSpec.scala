package v1.gth.parse

import org.scalacheck.Gen
import v1.gth.BaseSpec
import v1.gth.init.ShermanConsonant

class ParseSpec extends BaseSpec {
  "Factory method oneOf" should {
    "produce an invertible parser" in {
      import org.scalacheck.Shrink.shrinkAny

      val consonantDecorationMaps = Gen oneOf ShermanConsonant.table.values

      forAll(consonantDecorationMaps) {
        consonantDecorationMap =>
          val parse = Parse oneOf consonantDecorationMap.map(_.swap)

          consonantDecorationMap foreach {
            case (decoration, str) =>
              val Parse.Success(remaining, result) = parse(str)

              assert(remaining.isEmpty)
              result mustBe decoration
          }
      }
    }
  }
}
