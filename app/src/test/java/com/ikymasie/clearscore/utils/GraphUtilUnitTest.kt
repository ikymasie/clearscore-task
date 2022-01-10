package com.ikymasie.clearscore.utils

import org.junit.Test

import org.junit.Assert.*
import java.lang.Exception

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GraphUtilUnitTest {
    @Test
    fun givenValidInputs_whenExecuted_thenCorrectGraphValueReturned() {
        val util = GraphUtil()
        val expected = 90.0f
        val result = util.getGraphValue(25,100)
        assertEquals(expected,result)
    }

    @Test
    fun givenInvalidScore_whenExecuted_thenErrorIsThrown() {
        val util = GraphUtil()
        val expected = "Score must be greater than 0"
        try {
            util.getGraphValue(-25, 100)
        }catch (e: Exception){
            assertEquals(expected, e.message)
        }
    }

    @Test
    fun givenInvalidMaximumValue_whenExecuted_thenErrorIsThrown() {
        val util = GraphUtil()
        val expected = "Max score must be greater than 0"
        try {
            util.getGraphValue(25, 0)
        }catch (e: Exception){
            assertEquals(expected, e.message)
        }
    }

    @Test
    fun givenScoreLargerThanMax_whenExecuted_thenErrorIsThrown() {
        val util = GraphUtil()
        val expected = "Score must be LESS than the maximum:10"
        try {
            util.getGraphValue(25, 10)
        }catch (e: Exception){
            assertEquals(expected, e.message)
        }
    }
}