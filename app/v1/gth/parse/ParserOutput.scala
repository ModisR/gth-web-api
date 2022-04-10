package v1.gth.parse

sealed abstract class ParserOutput[+A](val isSuccess: Boolean)

final case class ParserSuccess[+A](remaining: String, result: A) extends ParserOutput[A](true)

final case class ParserFailure(errors: Seq[String] = Seq()) extends ParserOutput[Nothing](false)
