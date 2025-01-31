package agora.votecounter

import agora.votecounter.stv._
import agora.model._
import agora.model.{PreferenceBallot => Ballot}

import spire.math.Rational

/**
  * https://en.wikipedia.org/wiki/Exhaustive_ballot
  */

object InstantExhaustiveBallot extends VoteCounter[Ballot]
  with SimpleExclusionWithFixedElectionSize {

  override def winners(election: Election[Ballot], ccandidates: List[Candidate],  numVacancies: Int): List[(Candidate, Rational)] = {

    val ct = election.firstVotes(ccandidates)
    val sortedCandList = ct.toList.sortWith(_._2 < _._2)
    if (ct.size > 2) {
      val losingCand =  sortedCandList.head
      val newElection = exclude(election, losingCand._1)
      winners(newElection, ccandidates.filter(_ != losingCand._1), numVacancies)
    } else {
      sortedCandList.last::List()
    }
  }
}
