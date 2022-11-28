package poran.cse.github_top_rated_repo.data.utils

import poran.cse.github_top_rated_repo.data.model.AndroidRepo
import poran.cse.github_top_rated_repo.data.model.RepoOwner
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

object FakeAndroidRepoItemFactory {
    private  val counter = AtomicLong(0)

    fun createAndroidRepoItem(): AndroidRepo {
        val id = counter.incrementAndGet()
        return AndroidRepo(
            id = id,
            name = "Name $id",
            fullName = "full name",
            description = null,
            htmlUrl = "",
            owner = getFakeRepoOwner(),
            stars = 1,
            2,
            23,
            2,
            "",
            null,
            null
        )
    }
}


private fun getFakeRepoOwner() = RepoOwner(
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
    null,
)

