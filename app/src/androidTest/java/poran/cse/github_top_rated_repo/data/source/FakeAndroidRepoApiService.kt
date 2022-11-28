package poran.cse.github_top_rated_repo.data.source

import poran.cse.github_top_rated_repo.data.model.AndroidRepo
import poran.cse.github_top_rated_repo.data.source.remote.api.GithubApiService
import poran.cse.github_top_rated_repo.data.source.remote.dto.AndroidRepoResponse
import poran.cse.github_top_rated_repo.data.utils.FakeAndroidRepoItemFactory

class FakeAndroidRepoApiService: GithubApiService {
    private val fakeRepoItems: MutableList<AndroidRepo> = mutableListOf()
    var failureMsg: String? = null

    fun addAndroidRepo(moreStudyMore: AndroidRepo) {
        fakeRepoItems.add(moreStudyMore)
    }

    fun clearFakeAndroidRepos() {
        fakeRepoItems.clear()
    }

    fun createSomeFakeRepoItems() {
        fakeRepoItems.apply {
            add(FakeAndroidRepoItemFactory.createAndroidRepoItem())
            add(FakeAndroidRepoItemFactory.createAndroidRepoItem())
            add(FakeAndroidRepoItemFactory.createAndroidRepoItem())
            add(FakeAndroidRepoItemFactory.createAndroidRepoItem())
        }
    }

    override suspend fun getAndroidRepos(page: Int, itemsPerPage: Int): AndroidRepoResponse {
        //test for failure
        failureMsg?.let {
            throw Exception(it)
        }

        return AndroidRepoResponse(
            items = fakeRepoItems
        )
    }

}