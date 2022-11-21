package poran.cse.github_top_rated_repo.ui.uistates

import poran.cse.github_top_rated_repo.data.model.AndroidRepo

sealed class RepoDetailsUiState{
    data class DetailsRepoUiState(val data: AndroidRepo): RepoDetailsUiState()
    object EmptyState: RepoDetailsUiState()
}