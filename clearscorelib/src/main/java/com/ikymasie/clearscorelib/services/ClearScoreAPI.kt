package com.ikymasie.clearscorelib.services
import com.ikymasie.clearscorelib.model.CreditReport
import retrofit2.Response
import retrofit2.http.GET

interface ClearScoreAPI {
    @GET("/endpoint.json")
    suspend fun getBasicCreditReport() : Response<CreditReport>
}