package countvotes.algorithms

import countvotes.structures._
import countvotes.methods._
import collection.mutable.{HashMap => Map}
import java.io._


trait ACTExactWinnerRemoval extends STV[ACTBallot]{


  def removeWinnerWithoutSurplusFromElection(election: Election[ACTBallot], winner: Candidate): Election[ACTBallot] = {
   var list: Election[ACTBallot] = Nil
   for (b <- election if !b.preferences.isEmpty)
      if (b.preferences.head.name != winner.name) {
        list =  ACTBallot(filterPreferences(b.preferences, winner::List()),  b.id, b.marking, b.weight, b.value)::list
      }
   list
  }

}

// exactly like ACTExactWinnerRemoval
trait SenateExactWinnerRemoval extends STV[ACTBallot]{

  def removeWinnerWithoutSurplusFromElection(election: Election[ACTBallot], winner: Candidate): Election[ACTBallot] = {
   var list: Election[ACTBallot] = Nil
   for (b <- election if !b.preferences.isEmpty)
      if (b.preferences.head.name != winner.name) {
        list =  ACTBallot(filterPreferences(b.preferences, winner::List()),  b.id, b.marking, b.weight, b.value)::list
      }
   list
  }

}



trait ExactWinnerRemoval extends STV[Ballot]{


  def removeWinnerWithoutSurplusFromElection(election: Election[Ballot], winner: Candidate): Election[Ballot] = {
   var list: Election[Ballot] = Nil
   for (b <- election if !b.preferences.isEmpty)
      if (b.preferences.head.name != winner.name) {
        list =  Ballot(filterPreferences(b.preferences, winner::List()),  b.id,  b.weight)::list
      }
   list
  }

}
