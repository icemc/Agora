package agora.votecounter

import agora.parser.{CandidatesParser, PreferencesParser}
import agora.model.Candidate
import org.specs2.mutable.Specification


class InstantExhaustiveBallotTest extends Specification {

  val expectedInstantExhaustiveBallotWinnerList = List(Candidate("Knoxville"))

  "InstantExhaustiveBallot Test " should {

    "verify result" in { instantExhaustiveBallotVerification("14-example.e", "14-candidates.txt") shouldEqual expectedInstantExhaustiveBallotWinnerList }
  }

  def instantExhaustiveBallotVerification(electionFile: String, candidatesFile: String): List[Candidate] = {

    val candidates = CandidatesParser.read("../Agora/files/Examples/" + candidatesFile)
    val election =  PreferencesParser.read("../Agora/files/Examples/" + electionFile)

    InstantExhaustiveBallot.winners(election, candidates, 1).map {_._1}
  }
}
