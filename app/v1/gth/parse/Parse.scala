package v1.gth.parse

import scala.annotation.tailrec
import scala.language.postfixOps

abstract class Parse[+A] extends (String => Parse.Output[A]) {
  def map[B](f: A => B): Parse[B] = apply(_) map f

  def orElse[B >: A](parse: Parse[B]): Parse[B] = text => {
    apply(text) match {
      case output if output.isSuccess => output
      case _ => parse(text)
    }
  }
}

object Parse {
  sealed abstract class Output[+A](val isSuccess: Boolean) {
    def map[B](f: A => B): Output[B]
  }

  final case class Success[+A](remaining: String, result: A) extends Output[A](true) {
    def map[B](f: A => B): Output[B] = Success(remaining, f(result))
  }

  final case class Failure(errors: Seq[String] = Seq()) extends Output[Nothing](false) {
    def map[B](f: Nothing => B): Output[Nothing] = this
  }

  def apply[A](string: String)(implicit parse: Parse[A]): Output[A] = parse(string)

  def oneOf[A](items: Iterable[A]): Parse[A] = {
    val itemMap = items map { item => item.toString -> item } toMap
    val group = itemMap.keys mkString "|"
    val pattern = "(" + group + ")(.*)" r

    {
      case pattern(target, remaining) => Success(remaining, itemMap(target))
      case _ => Failure(Seq(s"Unable to match string to pattern: $pattern."))
    }
  }

  def many[A](implicit parse: Parse[A]): Parse[Seq[A]] = {
    @tailrec
    def parseImpl(acc: Seq[A], text: String): Output[Seq[A]] =
      parse(text) match {
        case Success(remaining, result) => parseImpl(acc :+ result, remaining)
        case _ => Success(text, acc)
      }

    parseImpl(Seq(), _)
  }
}
