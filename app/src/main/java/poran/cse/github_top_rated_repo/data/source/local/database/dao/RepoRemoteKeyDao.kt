package poran.cse.github_top_rated_repo.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import poran.cse.github_top_rated_repo.data.model.RepoRemoteKey

@Dao
interface RepoRemoteKeyDao {
    @Query("SELECT * FROM repo_remote_keys WHERE id = :id")
    suspend fun getRepoRemoteKey(id: Long): RepoRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepoRemoteKeys(repRemoteKeys : List<RepoRemoteKey>)

    @Query("DELETE FROM repo_remote_keys")
    suspend fun deleteRepoRemoteKeys(): Int

    @Query("SELECT lastUpdateTime FROM repo_remote_keys ORDER BY lastUpdateTime DESC LIMIT 1")
    suspend fun lastUpdated(): Long
}