package poran.cse.github_top_rated_repo.data.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * RepoRemoteKey will represent paging key table for paging 3
 */
@Keep
@Entity(tableName = "repo_remote_keys")
data class RepoRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)