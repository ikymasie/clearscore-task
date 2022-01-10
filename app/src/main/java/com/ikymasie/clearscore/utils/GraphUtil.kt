package com.ikymasie.clearscore.utils

import android.util.Log
import java.lang.Exception

class GraphUtil {
    fun getGraphValue(score: Long, maxScore: Long): Float {
        when {
            maxScore<=0 -> {
                throw Exception("Max score must be greater than 0")
            }
            score < 0 -> {
                throw Exception("Score must be greater than 0")
            }
            score > maxScore -> {
                throw Exception("Score must be LESS than the maximum:$maxScore")
            }
            else -> return 360.0f - ((1 - (score.toFloat() / maxScore.toFloat())) * 360)
        }


    }
}