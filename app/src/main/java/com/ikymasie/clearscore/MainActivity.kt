package com.ikymasie.clearscore

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.futured.donut.DonutProgressView
import app.futured.donut.DonutSection
import com.google.android.material.snackbar.Snackbar
import com.ikymasie.clearscore.utils.GraphUtil
import com.ikymasie.clearscore.viewmodels.CreditReportViewModel
import com.ikymasie.clearscore.views.DetailActivity
import com.ikymasie.clearscorelib.ClearScoreService
import com.ikymasie.clearscorelib.services.ClearScoreAPI
import com.robinhood.ticker.TickerUtils
import com.robinhood.ticker.TickerView
import kotlinx.coroutines.*
import java.lang.Exception
import android.util.Pair as UtilPair


@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"
    private var clearScoreApi: ClearScoreAPI? = null

    private lateinit var reportViewModel: CreditReportViewModel
    private val graphUtil: GraphUtil = GraphUtil()

    private lateinit var tvTopCircleText: TextView
    private lateinit var tvBottomCircleText: TextView
    private lateinit var tvMainCircleText: TickerView
    private lateinit var dvDonutView: DonutProgressView
    private lateinit var refresher: SwipeRefreshLayout
    private lateinit var containerOutline: ConstraintLayout
    private lateinit var tvTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.onBind()

    }

    /**
     * This is called to bind all newly instantiated objects/resources to the executing activity.
     * Called once
     * */
    private fun onBind(){
        // Views
        tvBottomCircleText = findViewById(R.id.tv_bottomCircleText)
        tvTopCircleText = findViewById(R.id.tv_topCircleText)
        tvMainCircleText = findViewById(R.id.tv_mainCircleText)
        containerOutline = findViewById(R.id.container_outline)
        tvTitle = findViewById(R.id.tv_title)
        tvMainCircleText.setCharacterLists(TickerUtils.provideNumberList()) // required
        tvMainCircleText.animationDuration = 1500
        dvDonutView = findViewById(R.id.dv_donutView)
        dvDonutView.setOnClickListener{
            onTapDetail()
        }
        refresher = findViewById(R.id.refresher)
        refresher.setOnRefreshListener {
            fetchData()
        }
        // View Model
        reportViewModel = ViewModelProvider(this).get(CreditReportViewModel::class.java)

        // observer
        reportViewModel.currentCreditReport.observe(this, {

            tvMainCircleText.text = it.creditReportInfo.score.toString()
            tvBottomCircleText.text = String.format(getString(R.string.credit_score_max_prefix,

            it.creditReportInfo.maxScoreValue.toString()))
                val section1 = DonutSection(
                    name = "Credit Score",
                    color = Color.parseColor("#FB1D32"),
                    amount = graphUtil.getGraphValue(it.creditReportInfo.score,
                        it.creditReportInfo.maxScoreValue)
                )
            dvDonutView.cap = 360f
            dvDonutView.submitData(listOf(section1))
        })

        // DAL
        clearScoreApi = ClearScoreService.getInstance().create(ClearScoreAPI::class.java)

        // fetch data onLoad to kickstart UI/UX
        fetchData()
    }
    /**
     * This is called to bind all newly instantiated objects/resources to the executing activity.
     * Called on each request to refresh data/UI
     * */
    private fun fetchData(){
        this.refresher.isRefreshing = true
        this.tvMainCircleText.text = "-"
        this.dvDonutView.clear()
        GlobalScope.launch {
            try {
                val result = clearScoreApi!!.getBasicCreditReport()
                if (result.body() !== null) {
                    val res = result.body()!!
                    Log.d(TAG,  res.toString())

                         GlobalScope.launch(Dispatchers.Main) {
                        reportViewModel.currentCreditReport.value = result.body()!!

                    }
                } else {
                    tapError("Oops, Unable to retrieve your score.. something went wrong...")
                }
            }catch (error: Exception) {
                Log.d(TAG, error.stackTrace.toString())
                tapError("Oops, Something went wrong...")
            }
            // stop refreshing animation
            GlobalScope.launch(Dispatchers.Main) {
                this@MainActivity.refresher.isRefreshing = false
            }
        }
    }

    /**
     * default handler => Snackbar
     * */
    private fun tapError(msg: String){
        val snackbar = Snackbar.make(window.decorView.rootView, msg, Snackbar.LENGTH_LONG)

        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.LTGRAY)

        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.BLACK)
        textView.textSize = 16f

        snackbar.show()
    }

    private fun onTapDetail(){
        var intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("data", reportViewModel.currentCreditReport.value)

        val donutPair = UtilPair.create<View?, String?>(dvDonutView,
            getString(R.string.tr_top_text_circle))

        val outlinePair = UtilPair.create<View?, String?>(containerOutline,
            getString(R.string.tr_circle_outline))

        val titlePair = UtilPair.create<View?, String?>(tvTitle,
            getString(R.string.tr_title))

        val topTextPair = UtilPair.create<View, String>(tvTopCircleText,
            getString(R.string.tr_top_text_circle))

        val mainTextPair = UtilPair.create<View, String>(tvMainCircleText,
            getString(R.string.tr_top_text_circle))

        val bottomTextPair = UtilPair.create<View, String>(tvBottomCircleText,
            getString(R.string.tr_top_text_circle))

        val options = ActivityOptions.makeSceneTransitionAnimation(this,donutPair,
            topTextPair, mainTextPair, bottomTextPair, outlinePair, titlePair)

        startActivity(intent, options.toBundle())
    }

}


