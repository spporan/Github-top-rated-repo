package poran.cse.github_top_rated_repo.ui.adapter.viewholders

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import poran.cse.github_top_rated_repo.databinding.RepoItemViewBinding
import poran.cse.github_top_rated_repo.ui.uistates.RepoUiModel
import poran.cse.github_top_rated_repo.util.numberFormat

class RepoItemViewHolder(
    private val binding: RepoItemViewBinding
): ViewHolder(binding.root) {

    fun onBind(repoUiItem: RepoUiModel.RepoItem) {
        binding.apply {
            Glide.with(itemView.context).load(repoUiItem.repo.owner.avatarUrl).into(profile)
            title.text = repoUiItem.repo.fullName
            description.text = repoUiItem.repo.description
            langName.text = repoUiItem.repo.language ?: ""
            starCount.text = numberFormat(repoUiItem.repo.stars.toLong())

        }
    }
}