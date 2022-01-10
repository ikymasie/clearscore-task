package com.ikymasie.clearscore.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ikymasie.clearscorelib.model.CreditReport

class CreditReportViewModel : ViewModel() {
    var creditReport: CreditReport? = null

    val currentCreditReport: MutableLiveData<CreditReport> by lazy {
        MutableLiveData<CreditReport>()
    }


}