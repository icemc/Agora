package agora.methods

import agora.methods.stv._

class EVACS extends ACT
 with TransferValueWithDenominatorWithNumOfMarkedContinuingBallotsOrOne
 with ACTSurplusDistribution
 with ACTNewWinnersDuringSurplusesDistribution
 with ACTNewWinnersDuringExclusion
 with ACTTotalsDuringExclusion