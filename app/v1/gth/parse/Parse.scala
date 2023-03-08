package v1.gth.parse

import v1.gth.init.Stringifiable
import v1.gth.parse.Parse.{Failure, Success}

import scala.annotation.tailrec
import scala.language.postfixOps

abstract class Parse[+A] extends (String => Parse.Output[A]) {
  def map[B](f: A => B): Parse[B] = apply(_) map f

  def flatMap[B](f: A => Parse[B]): Parse[B] =
    apply(_) match {
      case Success(remaining, result) => f(result)(remaining)
      case Failure(errors) => Failure(errors)
    }

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

  def apply[A](implicit parse: Parse[A]): Parse[A] = parse

  def apply[A](str: String)(implicit parse: Parse[A]): Output[A] = parse(str)

  def oneOf[A](items: Map[String, A]): Parse[A] = {
    val group = items.keys.toArray sortBy (-_.length) mkString "|"
    val pattern = "(" + group + ")(.*)" r

    {
      case pattern(target, remaining) => Success(remaining, items(target))
      case _ => Failure(Seq(s"Unable to match string to pattern: $pattern."))
    }
  }

  def oneOf[A <: Stringifiable](items: Iterable[A]): Parse[A] =
    oneOf(items map { item => item.asString -> item } toMap)

  implicit def option[A](implicit parse: Parse[A]): Parse[Option[A]] =
    str => {
      parse(str) match {
        case Success(remaining, result) => Success(remaining, Option(result))
        case Failure(_) => Success(str, None)
      }
    }

  implicit def seqOf[A](implicit parse: Parse[A]): Parse[Seq[A]] = {
    @tailrec
    def seqOfImpl(acc: Seq[A], text: String): Output[Seq[A]] =
      parse(text) match {
        case Success(remaining, result) => seqOfImpl(acc :+ result, remaining)
        case _ => Success(text, acc)
      }

    seqOfImpl(Seq(), _)
  }
}
