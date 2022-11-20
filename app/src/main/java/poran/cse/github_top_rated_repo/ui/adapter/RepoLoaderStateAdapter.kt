package poran.cse.github_top_rated_repo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import poran.cse.github_top_rated_repo.databinding.LoadingStateViewBinding
import javax.inject.Inject

class RepoLoaderStateAdapter @Inject constructor() :
    LoadStateAdapter<RepoLoaderStateAdapter.LoaderViewHolder>() {

    var onRetry: (() -> Unit)? = null

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder.getInstance(parent, retry = onRetry)
    }

    /**
     * view holder class for footer loader and error state handling
     */
    class LoaderViewHolder(private val binding: LoadingStateViewBinding, retry: (() -> Unit)?) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun getInstance(parent: ViewGroup, retry: (() -> Unit)?): LoaderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = LoadingStateViewBinding.inflate(inflater, parent, false)
                return LoaderViewHolder(binding, retry)
            }
        }

        init {
            binding.retryButton.setOnClickListener {
                retry?.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible =
                !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        }
    }
}