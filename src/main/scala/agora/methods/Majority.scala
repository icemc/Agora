package agora.methods

import agora.structures._
import agora.structures.{PreferenceBallot => Ballot}

object Majority extends VoteCounter[Ballot] {

  import spire.math.Rational
  
  // TODO: There is an implicit assumption here that all votes have weight 1.
  // Should this be checked?
  def winners(election: Election[Ballot], ccandidates: List[Candidate], numVacancies: Int ):
  List[(Candidate,Rational)] = {
      Election.totals(election, ccandidates).toList sortWith {
        (ct1, ct2) => ct1._2 > ct2._2
      } take(numVacancies) filter { case (c, t) => t > (election.length / 2) } // only select the alternative that has more than half the votes
  }
}
