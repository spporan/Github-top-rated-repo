package poran.cse.github_top_rated_repo.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import poran.cse.github_top_rated_repo.databinding.RepoItemViewBinding
import poran.cse.github_top_rated_repo.ui.adapter.listeners.RepoClickListener
import poran.cse.github_top_rated_repo.ui.adapter.viewholders.RepoItemViewHolder
import poran.cse.github_top_rated_repo.ui.uistates.RepoUiModel

class RepoPagingAdapter(
    private val onRepoItemClickListener: RepoClickListener
    ): PagingDataAdapter<RepoUiModel, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<RepoUiModel>() {
            override fun areItemsTheSame(oldItem: RepoUiModel, newItem: RepoUiModel): Boolean {
                return (oldItem as RepoUiModel.RepoItem).repo.id == (newItem as RepoUiModel.RepoItem).repo.id
            }

            override fun areContentsTheSame(oldItem: RepoUiModel, newItem: RepoUiModel): Boolean {
                return (oldItem as RepoUiModel.RepoItem).repo == (newItem as RepoUiModel.RepoItem).repo
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RepoItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel?.let {
            (holder as RepoItemViewHolder).onBind(it as RepoUiModel.RepoItem)
        }

    }

}