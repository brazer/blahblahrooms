package com.softeq.blahblahrooms.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val KEY_USER_ID = stringPreferencesKey("user_id")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    suspend fun putUserId(id: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = id
        }
    }

    fun getUserId(): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[KEY_USER_ID]
    }

}