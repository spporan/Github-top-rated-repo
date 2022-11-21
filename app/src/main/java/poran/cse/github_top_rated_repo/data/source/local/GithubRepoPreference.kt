package poran.cse.github_top_rated_repo.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.repoDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "poran.cse.github_top_rated_repo.preferences"
)
