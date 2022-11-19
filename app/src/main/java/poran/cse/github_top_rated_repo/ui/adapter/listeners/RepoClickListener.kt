package poran.cse.github_top_rated_repo.ui.adapter.listeners

import poran.cse.github_top_rated_repo.ui.uistates.RepoUiModel

interface RepoClickListener {
    fun onDetailsView(repoItem: RepoUiModel.RepoItem)
}