package poran.cse.github_top_rated_repo.data.repository.repoImpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import poran.cse.github_top_rated_repo.data.model.AndroidRepo
import poran.cse.github_top_rated_repo.data.repository.AndroidGithubRepoRepository
import poran.cse.github_top_rated_repo.data.source.local.database.RepoDatabase
import poran.cse.github_top_rated_repo.data.source.paging.AndroidRepoRemoteMediator
import poran.cse.github_top_rated_repo.data.source.remote.AndroidRepoNetworkDataSource
import javax.inject.Inject

class AndroidGithubRepoRepositoryImpl @Inject constructor(
    private val repoDatabase: RepoDatabase,
    private val networkDataSource: AndroidRepoNetworkDataSource
): AndroidGithubRepoRepository {
    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun loadAndroidRepo(): Flow<PagingData<AndroidRepo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = NETWORK_PAGE_SIZE,
            ),
            remoteMediator = AndroidRepoRemoteMediator(
                remoteDateSource = networkDataSource,
                database = repoDatabase,
            ),
            pagingSourceFactory = {
                repoDatabase.repoDao().getRepos()
            }
        ).flow
    }
}