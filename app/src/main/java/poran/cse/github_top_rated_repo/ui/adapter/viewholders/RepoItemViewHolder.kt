package poran.cse.github_top_rated_repo.ui.adapter.viewholders


import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import poran.cse.github_top_rated_repo.R
import poran.cse.github_top_rated_repo.databinding.RepoItemViewBinding
import poran.cse.github_top_rated_repo.ui.uistates.RepoUiModel
import poran.cse.github_top_rated_repo.util.generateDrawable
import poran.cse.github_top_rated_repo.util.numberFormat
import kotlin.random.Random


class RepoItemViewHolder(
    private val binding: RepoItemViewBinding
): ViewHolder(binding.root) {

    fun onBind(repoUiItem: RepoUiModel.RepoItem) {
        binding.apply {
            Glide.with(itemView.context).load(repoUiItem.repo.owner.avatarUrl).into(profile)
            title.text = repoUiItem.repo.fullName
            description.text = repoUiItem.repo.description
            langName.text = repoUiItem.repo.language ?: ""
            langPlaceholder.background = itemView.context.generateDrawable()
            if (repoUiItem.repo.language.isNullOrEmpty()) {
                langName.visibility = View.GONE
                langPlaceholder.visibility = View.GONE
            } else {
                langName.visibility = View.VISIBLE
                langPlaceholder.visibility = View.VISIBLE
            }
            starCount.text = numberFormat(repoUiItem.repo.stars.toLong())
        }
    }
}