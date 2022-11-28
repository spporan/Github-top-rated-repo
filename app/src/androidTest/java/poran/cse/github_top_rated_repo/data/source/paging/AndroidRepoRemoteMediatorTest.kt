@file:OptIn(ExperimentalPagingApi::class)

package poran.cse.github_top_rated_repo.data.source.paging

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import poran.cse.github_top_rated_repo.data.model.AndroidRepo
import poran.cse.github_top_rated_repo.data.source.FakeAndroidRepoApiService
import poran.cse.github_top_rated_repo.data.source.local.database.RepoDatabase
import poran.cse.github_top_rated_repo.data.source.remote.AndroidRepoNetworkDataSource

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class AndroidRepoRemoteMediatorTest {
    private lateinit var fakeApi: FakeAndroidRepoApiService
    private lateinit var remoteSource: AndroidRepoNetworkDataSource
    private lateinit var mockDb: RepoDatabase

    @Before
    fun setUp() {
        fakeApi = FakeAndroidRepoApiService()
        remoteSource = AndroidRepoNetworkDataSource(fakeApi)
        mockDb = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                RepoDatabase::class.java)
            .build()
    }


    //The first case is  when fakeApi returns valid data. The `load()`
    // method should return `MediatorResult.Success` and endOfPaginationReached property should be false

    @Test
    fun refreshLoadReturnsSuccessResultWhenAndroidRepoDataISPresent() = runTest {
        // Add mock results for the API to return.
        fakeApi.createSomeFakeRepoItems()

        val remoteMediator = AndroidRepoRemoteMediator(
            remoteSource,
            mockDb
        )

        val pagingState = PagingState<Int, AndroidRepo>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    //2nd test case is when fakeApi returns a  successful response but data  is  empty that time
    // `load()`  method should return `MediatorResult.Success` but `endOfPaginationReached` should be true.
    @Test
    fun refreshLoadReturnSuccessAndEndOfPaginationWhenNoMoreDataInThatApi() = runTest {
        val remoteMediator = AndroidRepoRemoteMediator(
            remoteSource,
            mockDb
        )

        val pagingState = PagingState<Int, AndroidRepo>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is  RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

    }

    // 3rd Test case is when fakeApi throw  an exception when fetching data. The `load()` method
    // should return `MediatorResult.Error`
    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccursForAndroidRepoFetch() = runTest {
        fakeApi.failureMsg = "No Internet  Connectivity. Please  check your connection"

        val remoteMediator = AndroidRepoRemoteMediator(
            remoteSource,
            mockDb
        )

        val pagingState = PagingState<Int, AndroidRepo>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result  =  remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }


    @After
    fun tearDown() {
        mockDb.close()
        fakeApi.clearFakeAndroidRepos()
    }
}