package agora.votecounter.stv

import scala.language.implicitConversions
import agora.model.PreferenceBallot
import spire.math.Rational
import agora.model.Candidate

class ACTBallot(p: List[Candidate], override val id: Int,  m:Boolean, w: Rational, v: Rational)
//extends MarkedBallot(p, id, m, w) with Value {
extends PreferenceBallot(p, id, w) with Value with Marking {
  val value = v
  val marking = m
}

object ACTBallot {
  def apply(p: List[Candidate], id: Int, m:Boolean, w: Rational, v: Rational): ACTBallot = new ACTBallot(p,id,m,w, v)

  implicit def fromBallot(b: PreferenceBallot): ACTBallot  = {
    new ACTBallot(b.preferences, b.id, true, b.weight, b.weight) // note that the marking is assigned true here
  }

}

trait Value extends PreferenceBallot {
  val value: Rational
}

trait Marking extends PreferenceBallot {
  val marking: Boolean
}
