package poran.cse.github_top_rated_repo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import poran.cse.github_top_rated_repo.data.repository.AndroidGithubRepoRepository
import poran.cse.github_top_rated_repo.ui.uistates.RepoUiModel
import javax.inject.Inject

class RepoListViewModel @Inject constructor(
    private val repository: AndroidGithubRepoRepository
): ViewModel() {

    fun loadRepo(): Flow<PagingData<RepoUiModel.RepoItem>> {
        return repository.loadAndroidRepo().map { paging ->
            paging.map {
                RepoUiModel.RepoItem(it)
            }
        }.cachedIn(viewModelScope)
    }
}