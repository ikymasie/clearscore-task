package com.ikymasie.clearscorelib

import java.io.Serializable

data class CreditReportInfo (
  val score: Long,
  val scoreBand: Long,
  val clientRef: String,
  val status: String,
  val maxScoreValue: Long,
  val minScoreValue: Long,
  val monthsSinceLastDefaulted: Long,
  val hasEverDefaulted: Boolean,
  val monthsSinceLastDelinquent: Long,
  val hasEverBeenDelinquent: Boolean,
  val percentageCreditUsed: Long,
  val percentageCreditUsedDirectionFlag: Long,
  val changedScore: Long,
  val currentShortTermDebt: Long,
  val currentShortTermNonPromotionalDebt: Long,
  val currentShortTermCreditLimit: Long,
  val currentShortTermCreditUtilisation: Long,
  val changeInShortTermDebt: Long,
  val currentLongTermDebt: Long,
  val currentLongTermNonPromotionalDebt: Long,
  val currentLongTermCreditLimit: Any? = null,
  val currentLongTermCreditUtilisation: Any? = null,
  val changeInLongTermDebt: Long,
  val numPositiveScoreFactors: Long,
  val numNegativeScoreFactors: Long,
  val equifaxScoreBand: Long,
  val equifaxScoreBandDescription: String,
  val daysUntilNextReport: Long
):  Serializable
