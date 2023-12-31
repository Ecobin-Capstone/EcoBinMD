package com.dicoding.ecobin.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[PHONE] = user.phoneNumber
            preferences[EMAIL_KEY] = user.email
            preferences[ID] = user.id
            preferences[NAME] = user.name
            preferences[LAT] = user.lat
            preferences[LONG] = user.long
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[PHONE] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[ID] ?: "",
                preferences[NAME] ?: "",
                preferences[LAT] ?: 0.0,
                preferences[LONG] ?: 0.0,
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PHONE = stringPreferencesKey("phoneNumber")
        private val ID = stringPreferencesKey("id")
        private val NAME= stringPreferencesKey("name")
        private val LAT= doublePreferencesKey("lat")
        private val LONG= doublePreferencesKey("long")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}