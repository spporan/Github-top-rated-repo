package poran.cse.github_top_rated_repo.data.source.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import poran.cse.github_top_rated_repo.data.model.AndroidRepo
import poran.cse.github_top_rated_repo.data.model.RepoRemoteKey
import poran.cse.github_top_rated_repo.data.source.local.database.RepoDatabase
import poran.cse.github_top_rated_repo.data.source.remote.AndroidRepoNetworkDataSource
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

private const val STARTING_PAGE_INDEX = 1

/**
 * This class is responsible for load item from the  remote server based on different type of LoadType
 * and  cache  it into the databse
 */
@OptIn(ExperimentalPagingApi::class)
class AndroidRepoRemoteMediator(
    private val remoteDateSource: AndroidRepoNetworkDataSource,
    private val database: RepoDatabase
) : RemoteMediator<Int, AndroidRepo>() {

    companion object {
        private const val TAG = "RepoMediator"
        private const val TIME_OUT_INTERVAL = 30L
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(TIME_OUT_INTERVAL, TimeUnit.MINUTES)

        val updatedTime = withContext(Dispatchers.IO) {
            database.remoteKeyDao().lastUpdated()
        }

        return if (System.currentTimeMillis() - updatedTime >= cacheTimeout)
        {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, AndroidRepo>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevPage
                if (prevKey == null) {
                    Log.d(TAG, "sending 1 success in mediator")
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextPage
                if (nextKey == null) {
                    Log.d(TAG, "sending 2 success in mediator")
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {

            val apiResponse = remoteDateSource.fetchRepos(page = page)
            Log.d(TAG, "loaded page no $page")

            val repoItems = apiResponse.items
            val endOfPaginationReached = repoItems.isEmpty()
            database.withTransaction {
                // clear repo table and  remote key table in the database cause sync with remote
                // and prevent data ambiguity
                if (loadType == LoadType.REFRESH && page == STARTING_PAGE_INDEX) {
                    database.remoteKeyDao().deleteRepoRemoteKeys()
                    database.repoDao().deleteRepos()
                }

                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repoItems.map {
                    RepoRemoteKey(id = it.id, prevPage = prevKey, nextPage = nextKey, lastUpdateTime = System.currentTimeMillis())
                }
                database.remoteKeyDao().insertRepoRemoteKeys(keys)

                //insert repo into the database table
                database.repoDao().insertRepos(repoItems)
            }
            Log.d(TAG, "sending 4 success in mediator")
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            Log.d(TAG, "sending 5 error in mediator")
            exception.printStackTrace()
            return MediatorResult.Error(exception)
        }
    }

    /**
     * get remote key for the last item
     */
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AndroidRepo>): RepoRemoteKey? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { repo ->
            database.remoteKeyDao().getRepoRemoteKey(
               repo.id
            )
        }
    }

    /**
     * get remote key for the first item
     */
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AndroidRepo>): RepoRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                database.remoteKeyDao().getRepoRemoteKey(
                   repo.id
                )
            }
    }

    /**
     * get remote key closest to the current scroll position
     */
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, AndroidRepo>): RepoRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeyDao().getRepoRemoteKey(id)
            }
        }
    }
}