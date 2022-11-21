package poran.cse.github_top_rated_repo.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import poran.cse.github_top_rated_repo.data.repository.AndroidGithubRepoRepository
import poran.cse.github_top_rated_repo.data.repository.repoImpl.AndroidGithubRepoRepositoryImpl
import poran.cse.github_top_rated_repo.data.source.local.database.RepoDatabase
import poran.cse.github_top_rated_repo.data.source.remote.AndroidRepoNetworkDataSource

@Module
@InstallIn(SingletonComponent::class)
object AndroidGithubRepoRepositoryModule {
    @Provides
    fun provideCategoryRepository(
        remoteRepoDataSource: AndroidRepoNetworkDataSource,
        repoDatabase: RepoDatabase,
        repoDataStore: DataStore<Preferences>,
        dispatcher: CoroutineDispatcher
    ): AndroidGithubRepoRepository =
        AndroidGithubRepoRepositoryImpl(
            repoDatabase = repoDatabase,
            remoteRepoDataSource,
            repoDataStore,
            dispatcher
        )
}