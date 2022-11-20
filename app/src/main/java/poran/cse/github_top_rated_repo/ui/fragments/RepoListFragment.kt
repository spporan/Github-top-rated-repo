package poran.cse.github_top_rated_repo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import poran.cse.github_top_rated_repo.databinding.RepositoryListFragmentBinding
import poran.cse.github_top_rated_repo.ui.adapter.RepoPagingAdapter
import poran.cse.github_top_rated_repo.ui.adapter.listeners.RepoClickListener
import poran.cse.github_top_rated_repo.ui.uistates.RepoUiModel
import poran.cse.github_top_rated_repo.ui.viewmodels.RepoListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class RepoListFragment : Fragment(),  RepoClickListener {

    private var _binding: RepositoryListFragmentBinding? = null

    private val binding get() = _binding!!

    private val viewModel: RepoListViewModel by viewModels()

    @Inject
    lateinit var repoAdapter: RepoPagingAdapter

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
    }

    private fun initView() {
       // repoAdapter = RepoPagingAdapter(this)
        repoAdapter.onRepoItemClickListener = this
        binding.repoList.apply {
            layoutManager =  LinearLayoutManager(context)
            adapter  = repoAdapter
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