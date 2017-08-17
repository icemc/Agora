package countvotes.structures

import play.api.libs.json._
import play.api.libs.functional.syntax._


case class MajorityBonus(jackpot: Double, bonus: Double)

object MajorityBonus {

  implicit val majorityBonusReader: Reads[MajorityBonus] = (
    (__ \ "jackpot").read[Double] and
      (__ \ "bonus").read[Double]
    ) (MajorityBonus.apply _)

  implicit val majorityBonusWriter: Writes[MajorityBonus] = (
    (__ \ "jackpot").write[Double] and
      (__ \ "bonus").write[Double]
    ) (unlift(MajorityBonus.unapply))
}

case class ComparisonSets(set1: Array[String], set2: Array[String])

object ComparisonSets {

  implicit val comparisonSetsReader: Reads[ComparisonSets] = (
    (__ \ "set1").read[Array[String]] and
      (__ \ "set2").read[Array[String]]
    ) (ComparisonSets.apply _)


  implicit val comparisonSetsWriter: Writes[ComparisonSets] = (
    (__ \ "set1").write[Array[String]] and
      (__ \ "set2").write[Array[String]]
    ) (unlift(ComparisonSets.unapply))

}

case class Parameters(comparisonOrder: Option[Array[String]], allowedVote: Option[Int], cutOffQuota: Option[Double],
                      proportionalRatio: Option[Double], majorityBonus: Option[MajorityBonus],
                      probabilityDistribution: Option[Array[Double]], comparisonSets: Option[ComparisonSets])

object Parameters {

  /*implicit val methodParamWriter: Writes[Parameters] = (
    (__ \ "comparison_order").write[Array[String]] and
      (__ \ "allowed_vote").write[Int] and
      (__ \ "cut_off_quota").write[Double] and
      (__ \ "proportional_ratio").write[Double] and
      (__ \ "majority_bonus").write[MajorityBonus] and
      (__ \ "probability_distribution").write[Array[Double]] and
      (__ \ "comparison_sets").write[ComparisonSets]
    ) (unlift(Parameters.unapply))*/

  implicit val methodParameterReader: Reads[Parameters] = (
    (__ \ "comparison_order").readNullable[Array[String]] and
      (__ \ "allowed_vote").readNullable[Int] and
      (__ \ "cut_off_quota").readNullable[Double] and
      (__ \ "proportional_ratio").readNullable[Double] and
      (__ \ "majority_bonus").readNullable[MajorityBonus] and
      (__ \ "probability_distribution").readNullable[Array[Double]] and
      (__ \ "comparison_sets").readNullable[ComparisonSets]
    ) (Parameters.apply _)

}






