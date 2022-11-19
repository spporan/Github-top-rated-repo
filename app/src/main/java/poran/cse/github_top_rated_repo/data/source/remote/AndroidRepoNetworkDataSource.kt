package poran.cse.github_top_rated_repo.data.source.remote

import poran.cse.github_top_rated_repo.data.source.remote.api.GithubApiService
import javax.inject.Inject

class AndroidRepoNetworkDataSource @Inject constructor(
    private val githubApi: GithubApiService
)  {
    suspend fun fetchRepos(
        page: Int,
        itemPerPage: Int
    ) = githubApi.getAndroidRepos(page, itemPerPage)
}
