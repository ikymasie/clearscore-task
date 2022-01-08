package com.ikymasie.clearscore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ikymasie.clearscorelib.ClearScoreService
import com.ikymasie.clearscorelib.model.CreditReport
import com.ikymasie.clearscorelib.services.ClearScoreAPI
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {
    lateinit var clearScoreApi: ClearScoreAPI

    // object fetched from successful API operation
    lateinit var custCreditReport: CreditReport

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.onBind()
    }

    /**
     * This is called to bind all newly instatiated objects/resources to the executing activity.
     * Called once
     * */
    private fun onBind(){
        clearScoreApi = ClearScoreService.getInstance().create(ClearScoreAPI::class.java)
        fetchData()
    }
    /**
     * This is called to bind all newly instatiated objects/resources to the executing activity.
     * Called on each request to refresh data/UI
     * */
    private fun fetchData(){
        GlobalScope.launch {
            val result = clearScoreApi.getBasicCreditReport()
            if (result.body() !== null){
                custCreditReport = result.body()!!
            }else{
                // throw error
            }
        }
    }


}