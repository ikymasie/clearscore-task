package com.ikymasie.clearscorelib

import org.junit.Test
import org.junit.Assert.*


class ClearScoreServiceUnitTest {
    val VALID_BASE_URL="https://android-interview.s3.eu-west-2.amazonaws.com"

    @Test
    fun instance_IsNot_Null() {
        val service = ClearScoreService.getInstance()

        assertNotNull(service)
        assertEquals(VALID_BASE_URL, service.baseUrl().toString())
    }
}