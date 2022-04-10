package v1.gth.parse

import scala.annotation.tailrec
import scala.language.postfixOps

abstract class Parses[+A] {
  def parse(text: String): ParserOutput[A]

  def map[B](f: A => B): Parses[B] =
    parse(_) match {
      case ParserSuccess(remaining, result) => ParserSuccess(remaining, f(result))
      case ParserFailure(errors) => ParserFailure(errors)
    }

  def orElse[B >: A](parser: Parses[B]): Parses[B] = text => {
    parse(text) match {
      case output if output.isSuccess => output
      case _ => parser parse text
    }
  }
}

object Parses {
  def oneOf[A](items: Iterable[A]): Parses[A] = new Parses[A] {
    private val itemMap = items map { item => item.toString -> item } toMap
    private val group = itemMap.keys mkString "|"
    private val pattern = "(" + group + ")(.*)" r

    def parse(text: String): ParserOutput[A] =
      text match {
        case pattern(target, remaining) => ParserSuccess(remaining, itemMap(target))
        case _ => ParserFailure()
      }
  }

  def many[A](parser: Parses[A]): Parses[Seq[A]] = text => {
    @tailrec
    def parseImpl(acc: Seq[A], text: String): ParserOutput[Seq[A]] =
      parser parse text match {
        case ParserSuccess(remaining, result) => parseImpl(acc :+ result, remaining)
        case _ => ParserSuccess(text, acc)
      }

    parseImpl(Seq(), text)
  }
}
