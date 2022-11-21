package poran.cse.github_top_rated_repo.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.ui.AppBarConfiguration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import poran.cse.github_top_rated_repo.R
import poran.cse.github_top_rated_repo.data.util.Sorting
import poran.cse.github_top_rated_repo.databinding.ActivityMainBinding
import poran.cse.github_top_rated_repo.ui.fragments.RepoListFragment
import poran.cse.github_top_rated_repo.ui.viewmodels.RepoListViewModel
import poran.cse.github_top_rated_repo.util.OnBackPressedDelegate
import poran.cse.github_top_rated_repo.util.OnBackPressedDelegateImpl


@AndroidEntryPoint
class AppMainActivity : AppCompatActivity(), OnBackPressedDelegate by OnBackPressedDelegateImpl() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModel: RepoListViewModel by viewModels()
    private var currentSortingOrder: Sorting = Sorting.DSC
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //handle on back pressed
        registerCallback(this, lifecycle) {
            Log.e("TTT", "handle on back pressed")
            if (supportFragmentManager.findFragmentById(R.id.container) is RepoListFragment) {
                finish()
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, RepoListFragment.newInstance(), RepoListFragment.TAG)
            .addToBackStack(RepoListFragment.TAG)
            .commit()

        initView()
    }

    private fun initView() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSortingOrder().collectLatest {
                    currentSortingOrder = it
                }
            }
        }

        binding.sortBtn.setOnClickListener {
            showPopup(it)
        }
    }

    private fun showPopup(v: View?) {
        val popup = v?.let { PopupMenu(this, it) }
        val inflater: MenuInflater? = popup?.menuInflater
        inflater?.inflate(R.menu.menu_main, popup.menu)

        when(currentSortingOrder) {
            Sorting.DSC -> {
                popup?.menu?.get(0)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_radio_button_unchecked_24)
                popup?.menu?.get(1)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_radio_button_checked_24)
            }
            Sorting.ASC -> {
                popup?.menu?.get(0)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_radio_button_checked_24)
                popup?.menu?.get(1)?.icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_radio_button_unchecked_24)
            }
        }

        popup?.setOnMenuItemClickListener { menu_item ->
            when (menu_item.itemId) {
                R.id.sort_asc -> {
                    Log.e("TTT", "click item")
                    viewModel.setSortingOrder(Sorting.ASC)
                }
                R.id.sort_desc -> {
                    viewModel.setSortingOrder(Sorting.DSC)
                }
            }
            true
        }
        popup?.setForceShowIcon(true)

        popup?.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.sort_asc -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}