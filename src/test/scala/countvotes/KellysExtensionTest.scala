import countvotes.methods.{KellysExtensionMethod, UncoveredSetMethod}
import countvotes.parsers.{CandidatesParser, ParameterParser, PreferencesParser}
import countvotes.structures.Candidate
import org.specs2.mutable.Specification

class KellysExtensionTest extends Specification {


  val expectedKellySet1 = Set(Candidate("A"))
  val expectedKellySet2 = Set(Candidate("A"), Candidate("B"))
  val expectedKellySet3 = Set(Candidate("B"))
  val expectedKellySet4 = Set(Candidate("B"), Candidate("C"))

  "UnconveredSet Test " should {

    "verify result" in { kellysExtensionVerification("35-example.e", "35-candidates.txt", "kellys-sets.json") shouldEqual expectedKellySet1 }
    "verify result" in { kellysExtensionVerification("35-example.e", "35-candidates.txt", "kellys-sets1.json") shouldEqual expectedKellySet2 }
    "verify result" in { kellysExtensionVerification("35-example.e", "35-candidates.txt", "kellys-sets2.json") shouldEqual expectedKellySet3 }
    "verify result" in { kellysExtensionVerification("35-example.e", "35-candidates.txt", "kellys-sets3.json") shouldEqual expectedKellySet4 }
    "verify result" in { kellysExtensionVerification("35-example.e", "35-candidates.txt", "kellys-sets4.json") shouldEqual expectedKellySet2 }
  }

  def kellysExtensionVerification(electionFile: String, candidatesFile: String, parameterFile: String): Set[Candidate] = {

    val candidates = CandidatesParser.read("../Agora/files/Examples/" + candidatesFile)
    val election =  PreferencesParser.read("../Agora/files/Examples/" + electionFile)
    val parameters = ParameterParser.parse("../Agora/files/Examples/" + parameterFile)

    KellysExtensionMethod.kellyPreferredSet(election, candidates, parameters, candidates.length).map {_._1}.toSet
  }

}
