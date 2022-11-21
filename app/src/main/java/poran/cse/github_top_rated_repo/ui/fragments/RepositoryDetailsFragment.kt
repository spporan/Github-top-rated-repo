package poran.cse.github_top_rated_repo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import poran.cse.github_top_rated_repo.data.model.AndroidRepo
import poran.cse.github_top_rated_repo.data.util.formatDateTime
import poran.cse.github_top_rated_repo.databinding.RepoDetailsFragmentBinding
import poran.cse.github_top_rated_repo.ui.uistates.RepoDetailsUiState
import poran.cse.github_top_rated_repo.ui.viewmodels.RepoListViewModel
import poran.cse.github_top_rated_repo.util.generateDrawable
import poran.cse.github_top_rated_repo.util.numberFormat

@AndroidEntryPoint
class RepositoryDetailsFragment : Fragment() {
    private val repoViewModel by activityViewModels<RepoListViewModel>()

    private var _binding: RepoDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "RepositoryDetails"
        private const val REPO_ID_PARAM = "repo_id_param"

        fun newInstance(repoId: Long): RepositoryDetailsFragment {
            val args = Bundle().apply {
                putLong(REPO_ID_PARAM, repoId)
            }
            return RepositoryDetailsFragment().apply {
                arguments = args
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RepoDetailsFragmentBinding.inflate(inflater, container, false)
        binding.root.setOnClickListener { /* prevent click in the bottom fragment item */ }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val repoID = it.getLong(REPO_ID_PARAM)
            repoViewModel.getRepoDetails(repoID)
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                repoViewModel.repoDetails.collectLatest { state ->
                    when(state) {
                        is RepoDetailsUiState.DetailsRepoUiState ->{
                            initView(state.data)
                        }
                        RepoDetailsUiState.EmptyState -> {
                            //empty state
                        }
                    }
                }
            }
        }
    }

    private fun initView(repoDetails: AndroidRepo) {
        binding.apply {
            Glide.with(requireContext()).load(repoDetails.owner.avatarUrl).into(profile)
            profileName.text = repoDetails.name.replaceFirstChar { it.titlecase() }
            title.text = repoDetails.fullName
            description.text = repoDetails.description
            langName.text = repoDetails.language ?: ""
            langPlaceholder.background = requireContext().generateDrawable()

            if (repoDetails.language.isNullOrEmpty()) {
                langName.visibility = View.GONE
                langPlaceholder.visibility = View.GONE
            } else {
                langName.visibility = View.VISIBLE
                langPlaceholder.visibility = View.VISIBLE
            }

            starCount.text = numberFormat(repoDetails.stars.toLong())
            issueCount.text = numberFormat(repoDetails.issues.toLong())
            forkCount.text = numberFormat(repoDetails.forks.toLong())
            watchCount.text = numberFormat(repoDetails.watchers.toLong())
            branchName.text = repoDetails.branch
            updatedAt.text = formatDateTime(repoDetails.updatedAt ?: "")

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}