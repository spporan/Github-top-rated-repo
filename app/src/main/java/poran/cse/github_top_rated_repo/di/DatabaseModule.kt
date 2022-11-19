package poran.cse.github_top_rated_repo.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import poran.cse.github_top_rated_repo.data.source.local.database.RepoDatabase
import poran.cse.github_top_rated_repo.data.source.local.database.dao.AndroidRepoDao
import poran.cse.github_top_rated_repo.data.source.local.database.dao.RepoRemoteKeyDao

@Module
@InstallIn(SingletonComponent::class)
object RepoDatabaseModule {

    @Provides
    fun provideRepoDatabase(app: Application): RepoDatabase =
        Room.databaseBuilder(app, RepoDatabase::class.java, RepoDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideRepoDao(db: RepoDatabase) : AndroidRepoDao = db.repoDao()

    @Provides
    fun provideRepoRemoteKeyDao(db: RepoDatabase) : RepoRemoteKeyDao = db.remoteKeyDao()
}