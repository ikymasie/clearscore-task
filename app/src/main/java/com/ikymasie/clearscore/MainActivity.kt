package com.ikymasie.clearscore

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.futured.donut.DonutProgressView
import com.google.android.material.snackbar.Snackbar
import com.ikymasie.clearscore.viewmodels.CreditReportViewModel
import com.ikymasie.clearscorelib.ClearScoreService
import com.ikymasie.clearscorelib.services.ClearScoreAPI
import com.robinhood.ticker.TickerUtils
import com.robinhood.ticker.TickerView
import kotlinx.coroutines.*
import java.lang.Exception


@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"
    private var clearScoreApi: ClearScoreAPI? = null

    private var instance: MainActivity? = null
    private lateinit var reportViewModel: CreditReportViewModel

    private lateinit var tvTopCircleText: TextView
    private lateinit var tvBottomCircleText: TextView
    private lateinit var tvMainCircleText: TickerView
    private lateinit var dvDonutView: DonutProgressView
    private lateinit var refresher: SwipeRefreshLayout

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
        instance = this
        // Views
        tvBottomCircleText = findViewById(R.id.tv_bottomCircleText)
        tvTopCircleText = findViewById(R.id.tv_topCircleText)
        tvMainCircleText = findViewById(R.id.tv_mainCircleText)
        tvMainCircleText.setCharacterLists(TickerUtils.provideNumberList()) // required
        tvMainCircleText.animationDuration = 1500
        dvDonutView = findViewById(R.id.dv_donutView)
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
        GlobalScope.launch {
            try {
                val result = clearScoreApi!!.getBasicCreditReport()
                if (result.body() !== null) {

                    Log.d(TAG,  result.body().toString())
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

}


