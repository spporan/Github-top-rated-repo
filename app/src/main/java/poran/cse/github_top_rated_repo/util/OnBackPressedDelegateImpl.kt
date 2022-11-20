package poran.cse.github_top_rated_repo.util

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * OnBack pressed delegate. It's the delegation of `OnBackPressedCallback`
 */
class OnBackPressedDelegateImpl: OnBackPressedDelegate, DefaultLifecycleObserver {
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            Log.e("CallBacks", "onBackPressedDelegate")
            onBackPressed?.invoke()

        }
    }

    private var fragmentActivity: FragmentActivity? = null
    private var onBackPressed: (() -> Unit)? = null

    override fun registerCallback(
        fragmentActivity: FragmentActivity?,
        lifecycle: Lifecycle,
        onBackPressed: () -> Unit
    ) {
        this.fragmentActivity = fragmentActivity
        this.onBackPressed = onBackPressed
        lifecycle.addObserver(this)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        fragmentActivity?.onBackPressedDispatcher?.addCallback(owner, onBackPressedCallback)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        onBackPressedCallback.remove()
    }
}