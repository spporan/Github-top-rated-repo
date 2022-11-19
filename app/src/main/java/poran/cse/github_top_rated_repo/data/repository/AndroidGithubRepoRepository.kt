package poran.cse.github_top_rated_repo.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import poran.cse.github_top_rated_repo.data.model.AndroidRepo

interface AndroidGithubRepoRepository {
    fun loadAndroidRepo(): Flow<PagingData<AndroidRepo>>
}