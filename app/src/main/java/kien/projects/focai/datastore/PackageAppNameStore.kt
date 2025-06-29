package kien.projects.focai.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.first


class PackageAppNameStore {

    private val key = stringPreferencesKey("PACKAGE_APP_NAMES")
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "package_app_names")
    }

    suspend fun addPackageAppNames(context: Context, packageNames: List<String>) {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(type)
        val json = adapter.toJson(packageNames)
        context.dataStore.edit {
            preferences ->
            preferences[key] = json
        }
    }

    suspend fun getPackageAppNames(context: Context): List<String>? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter: JsonAdapter<List<String>> = moshi.adapter(type)

        val preferences = context.dataStore.data.first()
        val json = preferences[key] ?: "[]"
        return adapter.fromJson(json)
    }
}