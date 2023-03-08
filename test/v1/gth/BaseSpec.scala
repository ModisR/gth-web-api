package v1.gth

import org.scalacheck.{Arbitrary, Gen}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import v1.gth.init.{ShermanConsonant, ShermanVowel}
import v1.gth.parse.{ConsonantStack, ShermanChar, VowelStack}
import Arbitrary.arbitrary

class BaseSpec extends PlaySpec with ScalaCheckPropertyChecks {
  override implicit val generatorDrivenConfig: PropertyCheckConfiguration = PropertyCheckConfiguration(minSuccessful = 50)

  implicit val arbConsonantStack: Arbitrary[ConsonantStack] =
    Arbitrary(
      for {
        head <- Gen oneOf ShermanConsonant.values
        tail <- Gen listOf (Gen oneOf ShermanConsonant.table(head.base).keys)
      } yield ConsonantStack(head, tail)
    )

  implicit val arbVowelStack: Arbitrary[VowelStack] =
    Arbitrary(
      for {
        head <- Gen oneOf ShermanVowel.values
        tail <- Gen listOf (Gen oneOf ShermanVowel.table(head.base).keys)
      } yield VowelStack(head, tail)
    )

  implicit val arbLetterStack: Arbitrary[ShermanChar.LetterStack] =
    Arbitrary(
      for {
        consonants <- arbitrary[Option[ConsonantStack]]
        vowels <- arbitrary[Option[VowelStack]] if consonants.isDefined || vowels.isDefined
      } yield ShermanChar.LetterStack(consonants, vowels)
    )

  implicit val arbShermanChar: Arbitrary[ShermanChar] =
    Arbitrary(
      Gen.frequency(
        8 -> arbitrary[ShermanChar.LetterStack],
        1 -> Gen.const(ShermanChar.Hyphen),
        1 -> Gen.const(ShermanChar.Apostrophe)
      )
    )
}
