package poran.cse.github_top_rated_repo.data.source.remote.api

import poran.cse.github_top_rated_repo.data.source.remote.dto.AndroidRepoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {
    /**
     * Get android repos ordered by stars.
     */
    @GET("search/repositories?sort=stars&q=android")
    suspend fun getAndroidRepos(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): AndroidRepoResponse

}