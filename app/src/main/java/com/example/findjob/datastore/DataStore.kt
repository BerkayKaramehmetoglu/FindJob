package com.example.findjob.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "user_prefs")
private val USER_UID_KEY = stringPreferencesKey("USER_UID")
private val USER_FAV_JOBS = stringSetPreferencesKey("USER_FAV_JOB")

suspend fun saveUserSession(context: Context, uid: String) {
    context.dataStore.edit { prefs ->
        prefs[USER_UID_KEY] = uid
    }
}

suspend fun addFavJobs(context: Context, item: String) {
    context.dataStore.edit { prefs ->
        val currentSet = prefs[USER_FAV_JOBS] ?: emptySet()
        prefs[USER_FAV_JOBS] = currentSet + item
    }
}

suspend fun getUserSession(context: Context): String? {
    val prefs = context.dataStore.data.first()
    return prefs[USER_UID_KEY]
}

suspend fun getFavJobs(context: Context): Set<String> {
    val prefs = context.dataStore.data.first()
    return prefs[USER_FAV_JOBS] ?: emptySet()
}


suspend fun clearUserSession(context: Context) {
    context.dataStore.edit { prefs ->
        prefs.remove(USER_UID_KEY)
    }
}

suspend fun removeFavJob(context: Context, item: String) {
    context.dataStore.edit { preferences ->
        val currentSet = preferences[USER_FAV_JOBS] ?: emptySet()
        val updatedSet = currentSet.toMutableSet().apply {
            remove(item)
        }
        preferences[USER_FAV_JOBS] = updatedSet
    }
}