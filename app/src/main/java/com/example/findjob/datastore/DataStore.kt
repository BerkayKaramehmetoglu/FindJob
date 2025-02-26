package com.example.findjob.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "user_prefs")
private val USER_UID_KEY = stringPreferencesKey("USER_UID")

suspend fun saveUserSession(context: Context, uid: String) {
    context.dataStore.edit { prefs ->
        prefs[USER_UID_KEY] = uid
    }
}

suspend fun getUserSession(context: Context): String? {
    val prefs = context.dataStore.data.first()
    return prefs[USER_UID_KEY]
}

suspend fun clearUserSession(context: Context) {
    context.dataStore.edit { prefs ->
        prefs.remove(USER_UID_KEY)
    }
}