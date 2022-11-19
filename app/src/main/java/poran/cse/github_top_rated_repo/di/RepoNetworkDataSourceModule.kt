package poran.cse.github_top_rated_repo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import poran.cse.github_top_rated_repo.data.repository.AndroidGithubRepoRepository
import poran.cse.github_top_rated_repo.data.source.remote.AndroidRepoNetworkDataSource
import poran.cse.github_top_rated_repo.data.source.remote.api.GithubApiService

@Module
@InstallIn(SingletonComponent::class)
object RepoNetworkDataSourceModule {
    @Provides
    fun provideRepoNetworkDataSourceModule(githubApiService: GithubApiService): AndroidRepoNetworkDataSource  {
        return AndroidRepoNetworkDataSource(githubApiService)
    }
}