package com.softeq.blahblahrooms.data.preferences

import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Test
import java.util.UUID

class AppPreferencesTest {

    private val preferences = AppPreferences(ApplicationProvider.getApplicationContext())

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testUserId() = runTest {
        val id = UUID.randomUUID().toString()
        preferences.putUserId(id)
        assertEquals(id, preferences.getUserId().first())
    }

}