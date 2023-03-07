package v1.gth

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class BaseSpec extends PlaySpec with ScalaCheckPropertyChecks {
  override implicit val generatorDrivenConfig: PropertyCheckConfiguration = PropertyCheckConfiguration(minSuccessful = 50)
}
