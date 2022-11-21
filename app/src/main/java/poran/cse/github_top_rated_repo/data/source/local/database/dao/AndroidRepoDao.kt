package poran.cse.github_top_rated_repo.data.source.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import poran.cse.github_top_rated_repo.data.model.AndroidRepo

@Dao
interface AndroidRepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(androidRepo: List<AndroidRepo>)

    @Query("SELECT * FROM android_repository ORDER BY stars DESC")
    fun getRepos(): PagingSource<Int, AndroidRepo>

    @Query("SELECT * FROM android_repository ORDER BY stars ASC")
    fun getAscendingSortedRepos(): PagingSource<Int, AndroidRepo>

    @Query("SELECT * FROM android_repository WHERE id = :repoId")
    suspend fun getRepoDetails(repoId: Long): AndroidRepo

    @Query("DELETE FROM android_repository")
    suspend fun deleteRepos(): Int
}