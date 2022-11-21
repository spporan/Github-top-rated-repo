package poran.cse.github_top_rated_repo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import poran.cse.github_top_rated_repo.data.source.local.repoDataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoDataStorePrefModule {
    // provides instance of DataStore
    @Provides
    @Singleton
    fun provideRepoDataStorePreferences(
        @ApplicationContext applicationContext: Context
    ): DataStore<Preferences> {
        return applicationContext.repoDataStore
    }
}