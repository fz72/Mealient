package gq.kirmanak.mealient.data.baseurl.impl

import androidx.datastore.preferences.core.Preferences
import gq.kirmanak.mealient.data.baseurl.ServerInfoStorage
import gq.kirmanak.mealient.data.storage.PreferencesStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerInfoStorageImpl @Inject constructor(
    private val preferencesStorage: PreferencesStorage,
) : ServerInfoStorage {

    private val baseUrlKey: Preferences.Key<String>
        get() = preferencesStorage.baseUrlKey

    private val serverVersionKey: Preferences.Key<String>
        get() = preferencesStorage.serverVersionKey

    override suspend fun getBaseURL(): String? = getValue(baseUrlKey)

    override suspend fun storeBaseURL(baseURL: String, version: String) {
        preferencesStorage.storeValues(
            Pair(baseUrlKey, baseURL),
            Pair(serverVersionKey, version),
        )
    }

    override suspend fun getServerVersion(): String? = getValue(serverVersionKey)

    override suspend fun storeServerVersion(version: String) {
        preferencesStorage.storeValues(Pair(serverVersionKey, version))
    }

    private suspend fun <T> getValue(key: Preferences.Key<T>): T? = preferencesStorage.getValue(key)
}