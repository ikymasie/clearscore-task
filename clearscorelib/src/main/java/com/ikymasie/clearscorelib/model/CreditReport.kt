package com.ikymasie.clearscorelib.model

import com.ikymasie.clearscorelib.CoachingSummary
import com.ikymasie.clearscorelib.CreditReportInfo
import java.io.Serializable

data class CreditReport(
    val accountIDVStatus: String,
    val creditReportInfo: CreditReportInfo,
    val dashboardStatus: String,
    val personaType: String,
    val coachingSummary: CoachingSummary,
    val augmentedCreditScore: Any? = null
) : Serializable
