package countvotes.methods

import countvotes.structures._

import scala.collection.mutable.{HashSet, HashMap => Map}

abstract class VoteCounter[B <: Ballot] {

 def totals(election: Election[B], candidates: List[Candidate]): Map[Candidate, Rational] = {
    val m = new Map[Candidate, Rational]

    for (c<-candidates) m(c) = 0

    for (b <- election if !b.preferences.isEmpty) {
      m(b.preferences.head) = b.weight + (m.getOrElse(b.preferences.head, 0))
    }
    m
 }

 def vacanciesFilled(numWinners:Int, numVacancies:Int): Boolean =
    numWinners >= numVacancies

 // When candidates' names are from 1 to N
 // Implemented to compare EVoting outputs with Jeremy's outputs
 def generateNIntCandidates(n: Integer): List[Candidate] = {
  var lcand: List[Candidate] = Nil
  for (i <- 1 to n){
    lcand = (Candidate(i.toString(),None,None)):: lcand
  }
  lcand
 }

 def getCandidates(election: Election[B]): List[Candidate] = {
   var set = new HashSet[Candidate]()
   for (b <- election) {
     for (c <- b.preferences)
       if (!set.exists(n => n == c) ) set = set + c
    }
   set.toList
  }

  // just printing in terminal
 def printElection(election: Election[B]): Unit = {
    print("\n")
    for (e <- election.sortBy(x => x.id)) {
      var pr = ""
      for (p <- e.preferences) pr = pr + p + " > "
      println(e.id + "   " + pr.dropRight(2) + "  " + e.weight)
    }
    print("\n")
 }

 def printTotal(total: Map[Candidate, Rational]): Unit = {
    print("\n")
    for (t <- total) {
      var pr = ""
      println(t)
    }
    print("\n")
 }

  // utility method for matrix where a[i][j] = x means candidate i has got #x votes against candidate j
  def getPairwiseComparisonForWeightedElection(election: Election[Ballot], candidates: List[Candidate]): Array[Array[Rational]] = {

    val zeroRational = Rational(0, 1)
    val responseMatrix = Array.fill(candidates.size, candidates.size)(Rational(0, 1))

    for (b <- election if b.preferences.nonEmpty) {
      b.preferences.zipWithIndex foreach { case (c1,i1) => {
        b.preferences.zipWithIndex foreach { case (c2,i2) => {
          if (i1 < i2) {
            responseMatrix(candidates.indexOf(c1))(candidates.indexOf(c2)) += b.weight
          }}}}}}
    responseMatrix
  }
  
  
  def winners(e: Election[B], ccandidates: List[Candidate], numVacancies: Int): List[(Candidate,Rational)]
}

trait Scrutiny[B <: Ballot] extends VoteCounter[B] {

  protected val result: Result = new Result
  protected val report: Report[B] = new Report[B]

  def runScrutiny(election: Election[B], candidates: List[Candidate], numVacancies: Int):   Report[B]  = {

    print("\n INPUT ELECTION: \n")
    printElection(election)

    var tls = totals(election, candidates)

    result.addTotalsToHistory(tls)

    report.setCandidates(candidates)

    report.newCount(Input, None, None, Some(tls), None, None)

    report.setWinners(winners(election, candidates, numVacancies))

    report
  }


}


