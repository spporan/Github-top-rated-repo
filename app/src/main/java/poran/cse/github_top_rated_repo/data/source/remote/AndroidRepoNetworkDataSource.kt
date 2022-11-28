package poran.cse.github_top_rated_repo.data.source.remote

import poran.cse.github_top_rated_repo.data.source.remote.api.GithubApiService
import javax.inject.Inject

open class AndroidRepoNetworkDataSource @Inject constructor(
    private val githubApi: GithubApiService
)  {
    companion object{
        const val ITEM_PER_PAGE  = 10
    }
    suspend fun fetchRepos(
        page: Int,
        itemPerPage: Int = ITEM_PER_PAGE
    ) = githubApi.getAndroidRepos(page, itemPerPage)
}
