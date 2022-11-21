package poran.cse.github_top_rated_repo.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import poran.cse.github_top_rated_repo.data.model.AndroidRepo
import poran.cse.github_top_rated_repo.data.util.Sorting

interface AndroidGithubRepoRepository {
    fun loadAndroidRepo(sorting: Sorting): Flow<PagingData<AndroidRepo>>

    suspend fun setCurrentSortingOrder(sorting: Sorting)

    fun getCurrentSortingOrder(): Flow<Sorting>
}