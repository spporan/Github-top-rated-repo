package poran.cse.github_top_rated_repo.ui.uistates

import androidx.paging.PagingData
import poran.cse.github_top_rated_repo.data.model.AndroidRepo

sealed class RepoUiModel {
    data class RepoItem(val repo: AndroidRepo) : RepoUiModel()
}

data class RepoListUiState(val pagedData: PagingData<RepoUiModel>)
