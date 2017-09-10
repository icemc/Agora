package agora.structures

 class ACTCandidate(val ecode: Int, val pcode: Int, val ccode: Int, override val name: String,
   override val id: Option[Int] = None, override val party: Option[String] = None) extends Candidate(name, id, party)

