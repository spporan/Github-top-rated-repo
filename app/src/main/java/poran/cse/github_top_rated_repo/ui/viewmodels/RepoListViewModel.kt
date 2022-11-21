package poran.cse.github_top_rated_repo.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import poran.cse.github_top_rated_repo.data.repository.AndroidGithubRepoRepository
import poran.cse.github_top_rated_repo.data.util.Sorting
import poran.cse.github_top_rated_repo.ui.uistates.RepoUiModel
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val repository: AndroidGithubRepoRepository
): ViewModel() {

     private var _pageData: Flow<PagingData<RepoUiModel>>? = null
     val  pageData = _pageData

    fun loadRepo(sorting: Sorting = Sorting.DSC) : Flow<PagingData<RepoUiModel>>{
        return repository.loadAndroidRepo(sorting).map { paging ->
            paging.map {
                RepoUiModel.RepoItem(it) as RepoUiModel
            }
        }.cachedIn(viewModelScope)
    }

    fun setSortingOrder(sorting:Sorting) {
        viewModelScope.launch {
            repository.setCurrentSortingOrder(sorting)
        }
    }

    fun getSortingOrder():Flow<Sorting> {
       return repository.getCurrentSortingOrder()
    }
}