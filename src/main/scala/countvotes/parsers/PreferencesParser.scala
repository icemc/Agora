package countvotes.parsers


import countvotes._
import countvotes.structures._

import scala.util.parsing.combinator._
import scala.util.matching.Regex

trait ElectionParsers extends RegexParsers {

  def candidate: Parser[Candidate] = """[0-9A-Za-z\-\,\.\ \']*""".r ^^ { s => Candidate(s) }

  def numerator: Parser[BigInt] = """[0-9]*""".r ^^ { s => BigInt(s) }

  def denominator: Parser[BigInt] = """[0-9]*""".r ^^ { s => BigInt(s) }

  def id: Parser[Int] = """[0-9]+""".r ^^ { _.toInt }
}


object PreferencesParser extends ElectionParser[WeightedBallot] with RegexParsers with ElectionParsers {
  
  def preferences: Parser[List[Candidate]] = repsep(candidate, ">")

  // the method line returns a Parser of type ACTBallotPapersDataStructure
  def line: Parser[WeightedBallot] = id ~ numerator ~ "/" ~ denominator ~ preferences ^^ {
    case ~(~(~(~(i, n), "/"), d), p) => {
      //println(p)
      WeightedBallot(p, i, Rational(n, d))
    }
  }
}

object PreferencesParserWithRankAndScore extends ElectionParser[WeightedBallot] with RegexParsers with ElectionParsers {

  def choice: Parser[(Candidate, Option[Int], Option[Int])] = candidate ~ ";" ~ opt(rank) ~ ";" ~ opt(score) ^^ {
        case ~(~(~(~(candidate, ";"), rank), ";"), score) => {
          (candidate, rank, score)
        }
      }
  
  def preferences: Parser[List[(Candidate, Option[Int], Option[Int])]] = repsep(choice, ")(") ^^ {
    case p => p sortWith { 
        case ((_, Some(r1), _), (_, Some(r2), _)) => r1 < r2        // Sorting by rank, if rank is available
        case ((_, None, Some(s1)), (_, None, Some(s2))) => s1 > s2  // Sorting by score, if rank is not available and score is
        case (_, _) => true                                         // Leaving unsorted, if neither rank nor score is available
    }
  }
  
  def rank: Parser[Int] = """[0-9]+""".r ^^ { _.toInt }

  def score: Parser[Int] = """[0-9]+""".r ^^ { _.toInt }
  
  // the method line returns a Parser of type ACTBallotPapersDataStructure
  def line: Parser[WeightedBallot] = id ~ numerator ~ "/" ~ denominator ~ "(" ~ preferences ~ ")" ^^ {
    case ~(~(~(~(~(~(i, n), "/"), d), "("), p), ")") => {

      WeightedBallot(p map {_._1}, i, Rational(n, d))
    }
  }
}

object PreferencesWithoutIDAndWeightParser extends ElectionParser[WeightedBallot] with RegexParsers with ElectionParsers {

  def preferences: Parser[List[Candidate]] = repsep(candidate, ")(")
  
  def line: Parser[WeightedBallot] = preferences ^^ {
    case p => {
      //println(p)
      WeightedBallot(p, 0, Rational(1, 1))
    }
  }
}

