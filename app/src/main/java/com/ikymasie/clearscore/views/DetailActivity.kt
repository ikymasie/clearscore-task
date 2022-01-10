package com.ikymasie.clearscore.views

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import app.futured.donut.DonutProgressView
import app.futured.donut.DonutSection
import com.ikymasie.clearscore.R
import com.ikymasie.clearscore.utils.GraphUtil
import com.ikymasie.clearscore.viewmodels.CreditReportViewModel
import com.ikymasie.clearscorelib.model.CreditReport
import com.robinhood.ticker.TickerUtils
import com.robinhood.ticker.TickerView

class DetailActivity : AppCompatActivity() {

    private lateinit var tvTopCircleText: TextView
    private lateinit var tvBottomCircleText: TextView
    private lateinit var tvMainCircleText: TickerView
    private lateinit var dvDonutView: DonutProgressView
    // just added two more textviews to show data is passed across
    private lateinit var tvStatus: TextView
    private lateinit var tvClientRef: TextView
    private lateinit var btnBack: ImageButton


    private lateinit var reportDetail: CreditReport
    private lateinit var reportViewModel: CreditReportViewModel
    private val graphUtil: GraphUtil = GraphUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        this.onBind()
    }

    private fun onBind(){
        // Views
        tvClientRef = findViewById(R.id.tv_clientRef)
        tvStatus = findViewById(R.id.tv_status)
        tvBottomCircleText = findViewById(R.id.tv_bottomCircleText)
        tvTopCircleText = findViewById(R.id.tv_topCircleText)
        tvMainCircleText = findViewById(R.id.tv_mainCircleText)
        tvMainCircleText.setCharacterLists(TickerUtils.provideNumberList()) // required
        tvMainCircleText.animationDuration = 500
        dvDonutView = findViewById(R.id.dv_donutView)
        dvDonutView.cap = 360f

        // back button click
        btnBack = findViewById(R.id.btn_back)
        btnBack.setOnClickListener {
            this.onBackPressed() // or finish (if im passing data back)
        }

        // View Model
        reportDetail = intent.getSerializableExtra("data") as CreditReport

        reportViewModel = ViewModelProvider(this).get(CreditReportViewModel::class.java)
        reportViewModel.currentCreditReport.observe(this, {

            tvMainCircleText.text = it.creditReportInfo.score.toString()
            tvStatus.text = it.dashboardStatus

            tvBottomCircleText.text = String.format(getString(R.string.credit_score_max_prefix,
                it.creditReportInfo.maxScoreValue.toString()))

            tvClientRef.text = String.format(getString(R.string.credit_score_client_ref_prefix,
                it.creditReportInfo.clientRef))
                val section1 = DonutSection(
                name = "Credit Score",
                color = Color.parseColor("#FB1D32"),
                amount = graphUtil.getGraphValue(
                    it.creditReportInfo.score,
                    it.creditReportInfo.maxScoreValue
                )
            )

            dvDonutView.submitData(listOf(section1))

            // additional detail items go here
        })
        reportViewModel.currentCreditReport.value = reportDetail
    }
}