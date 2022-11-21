package poran.cse.github_top_rated_repo.data.repository.repoImpl

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.paging.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import poran.cse.github_top_rated_repo.data.model.AndroidRepo
import poran.cse.github_top_rated_repo.data.repository.AndroidGithubRepoRepository
import poran.cse.github_top_rated_repo.data.source.local.database.RepoDatabase
import poran.cse.github_top_rated_repo.data.source.paging.AndroidRepoRemoteMediator
import poran.cse.github_top_rated_repo.data.source.remote.AndroidRepoNetworkDataSource
import poran.cse.github_top_rated_repo.data.util.SORTING_CACHE_PARAM
import poran.cse.github_top_rated_repo.data.util.Sorting
import poran.cse.github_top_rated_repo.data.util.toSortedOrder
import javax.inject.Inject

class AndroidGithubRepoRepositoryImpl @Inject constructor(
    private val repoDatabase: RepoDatabase,
    private val networkDataSource: AndroidRepoNetworkDataSource,
    private val repoDataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher
): AndroidGithubRepoRepository {
    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }

    private var sortingOrder = Sorting.DSC

    private val pagingSource  = InvalidatingPagingSourceFactory {
        Log.e("DDD","sort  $sortingOrder")
        when(sortingOrder) {
            Sorting.DSC -> {
                repoDatabase.repoDao().getRepos()

            }
            Sorting.ASC -> {
                repoDatabase.repoDao().getAscendingSortedRepos()

            }
        }

        //repoDatabase.repoDao().getAscendingSortedRepos()
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun loadAndroidRepo(sorting: Sorting): Flow<PagingData<AndroidRepo>> {

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
            pagingSourceFactory = pagingSource
        ).flow
    }

    override suspend fun setCurrentSortingOrder(sorting: Sorting) {
        Result.runCatching {
            repoDataStore.edit { preferences ->
                preferences[SORTING_CACHE_PARAM] = sorting.name
            }
        }
    }

    override fun getCurrentSortingOrder(): Flow<Sorting> {
        return repoDataStore.data.catch { exception ->
            Log.e("EEE", "error ${exception.message}")
        }.map { preferences ->
            val sortOrder =  preferences[SORTING_CACHE_PARAM]?.toSortedOrder() ?: Sorting.DSC
            Log.e("EEE", "sortOrder $sortOrder")
            sortingOrder = sortOrder
            sortOrder

        }
    }

    override suspend fun getRepoDetails(repoId: Long): AndroidRepo {
        return withContext(dispatcher) {
            repoDatabase.repoDao().getRepoDetails(repoId)
        }
    }
}