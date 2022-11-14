package com.softeq.blahblahrooms.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppPreferences(
    private val appContext: Context
) {

    companion object {
        private val KEY_USER_ID = stringPreferencesKey("user_id")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    suspend fun putUserId(id: String) {
        appContext.dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = id
        }
    }

    fun getUserId(): Flow<String?> = appContext.dataStore.data.map { preferences ->
        preferences[KEY_USER_ID]
    }

}