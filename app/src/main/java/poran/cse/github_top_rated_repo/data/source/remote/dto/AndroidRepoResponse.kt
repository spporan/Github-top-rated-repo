package poran.cse.github_top_rated_repo.data.source.remote.dto

import com.google.gson.annotations.SerializedName
import poran.cse.github_top_rated_repo.data.model.AndroidRepo

/**
 * Data class to hold android repository responses from  API calls.
 */
data class AndroidRepoResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<AndroidRepo>,
)