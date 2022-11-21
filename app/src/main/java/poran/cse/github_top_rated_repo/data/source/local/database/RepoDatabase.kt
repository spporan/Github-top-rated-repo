package poran.cse.github_top_rated_repo.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import poran.cse.github_top_rated_repo.data.model.AndroidRepo
import poran.cse.github_top_rated_repo.data.model.RepoRemoteKey
import poran.cse.github_top_rated_repo.data.model.TypeConverter
import poran.cse.github_top_rated_repo.data.source.local.database.dao.AndroidRepoDao
import poran.cse.github_top_rated_repo.data.source.local.database.dao.RepoRemoteKeyDao

@Database(entities = [
    AndroidRepo::class,
    RepoRemoteKey::class
], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class RepoDatabase: RoomDatabase() {

    abstract fun repoDao(): AndroidRepoDao

    abstract fun remoteKeyDao(): RepoRemoteKeyDao

    companion object {
        const val DATABASE_NAME = "repo_database"
    }
}