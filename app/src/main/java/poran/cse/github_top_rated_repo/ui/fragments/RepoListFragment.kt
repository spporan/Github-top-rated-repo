package poran.cse.github_top_rated_repo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import poran.cse.github_top_rated_repo.R
import poran.cse.github_top_rated_repo.databinding.RepositoryListFragmentBinding
import poran.cse.github_top_rated_repo.ui.adapter.RepoLoaderStateAdapter
import poran.cse.github_top_rated_repo.ui.adapter.RepoPagingAdapter
import poran.cse.github_top_rated_repo.ui.adapter.listeners.RepoClickListener
import poran.cse.github_top_rated_repo.ui.uistates.RepoUiModel
import poran.cse.github_top_rated_repo.ui.viewmodels.RepoListViewModel
import poran.cse.github_top_rated_repo.util.ItemDecoration
import javax.inject.Inject

@AndroidEntryPoint
class RepoListFragment : Fragment(),  RepoClickListener {

    private var _binding: RepositoryListFragmentBinding? = null

    private val binding get() = _binding!!

    private val viewModel: RepoListViewModel by activityViewModels()

    @Inject
    lateinit var repoAdapter: RepoPagingAdapter

    @Inject
    lateinit var repoLoaderStateAdapter: RepoLoaderStateAdapter

    companion object {
        const val TAG  = "RepoListFragment"

        fun newInstance(): RepoListFragment{
            val args = Bundle()

            val fragment = RepoListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RepositoryListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadRepo().collectLatest { pageData ->
                    repoAdapter.submitData(pageData)
                }
            }

        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSortingOrder().collectLatest {
                    Log.e("ERR", "collect sort order $it")
                    //viewModel.loadRepo(it)
                    repoAdapter.refresh()
                }
            }
        }
    }

    /**
     * Initialize view here
     */
    private fun initView() {

        repoLoaderStateAdapter.onRetry = {
            repoAdapter.retry()
        }

        repoAdapter.onRepoItemClickListener = this
        binding.repoList.apply {
            layoutManager =  LinearLayoutManager(context)
            adapter  = repoAdapter.withLoadStateFooter(repoLoaderStateAdapter)
            addItemDecoration(ItemDecoration(
                    firstItemTopMargin = resources.getDimensionPixelOffset(R.dimen.common_margin
                    ),
                    lastItemBottomMargin = resources.getDimensionPixelOffset(R.dimen.common_margin)
            ))
        }

        //page  state observer
        repoAdapter.addLoadStateListener { loadState ->

            val isEmptyData = repoAdapter.itemCount == 0
            val isError = loadState.refresh is LoadState.Error && isEmptyData
            val isEmptyDataState = loadState.mediator?.append is LoadState.NotLoading
                    && isEmptyData
                    && loadState.mediator?.append?.endOfPaginationReached == true

            if (loadState.refresh is LoadState.Loading && isEmptyData){
                binding.loader.visibility = View.VISIBLE
            }
            else if (isError || isEmptyDataState) {
                // getting the error and gone loader
                binding.errorMsg.isVisible = true
                binding.retryButton.isVisible = true

                binding.loader.isVisible = false
            } else {
                //gone error placeholder here
                binding.loader.isVisible = isEmptyData
                binding.errorMsg.isVisible = false
                binding.retryButton.isVisible = false
            }
            binding.retryButton.setOnClickListener {
                repoAdapter.retry()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetailsView(repoItem: RepoUiModel.RepoItem) {
        TODO("Not yet implemented")
    }
}