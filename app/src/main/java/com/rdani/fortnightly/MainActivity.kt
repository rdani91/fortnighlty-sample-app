package com.rdani.fortnightly

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.rdani.fortnightly.databinding.ActivityMainBinding
import com.rdani.fortnightly.feature.news.ArticleDetailScreen
import com.rdani.fortnightly.feature.news.ArticleListScreen
import com.rdani.fortnightly.model.Article
import com.rdani.fortnightly.utils.DetailsTransition
import com.rdani.fortnightly.utils.px
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val mainViewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(requireNotNull(binding).root)

        initBackStackListener()
        initToolbar()
        mainViewModel.selectedArticle.observe(this, ::handleSelectedArticle)
        loadFragment(ArticleListScreen())

    }

    private fun initBackStackListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            val backstackCount = supportFragmentManager.backStackEntryCount
            if (backstackCount > 0) {
                binding?.imgMenu?.setImageResource(R.drawable.ic_baseline_arrow_back_24)
                handleToolbarState(toolbarOpen = true, isShort = true)
            } else {
                binding?.imgMenu?.setImageResource(R.drawable.ic_baseline_menu_24)
                handleToolbarState(mainViewModel.toolbarSizeState.value ?: false, false)
            }
        }
    }

    private fun handleSelectedArticle(article: Article?) {
        article?.let {
            loadFragment(ArticleDetailScreen.newInstance(article))
        }
    }

    private fun initToolbar() {
        mainViewModel.toolbarSizeState.observe(this, ::handleToolbarState)

        val shape = ShapeAppearanceModel.builder()
            .setBottomRightCorner(CornerFamily.CUT, 20.px.toFloat())
            .build()

        val drawable = MaterialShapeDrawable(shape).apply {
            this.tintList = ColorStateList.valueOf(resources.getColor(android.R.color.white))
        }

        binding?.toolbar?.background = drawable

        binding?.imgMenu?.setOnClickListener {
            val backstackCount = supportFragmentManager.backStackEntryCount

            if (backstackCount > 0) {
                supportFragmentManager.popBackStack()
            }
        }
    }

    private fun handleToolbarState(toolbarOpen: Boolean, isShort: Boolean = false) {
        binding?.root?.let { root ->

            val transition: Transition = ChangeBounds()
            transition.duration = 300

            TransitionManager.beginDelayedTransition(root, transition)

            val constraintSet = ConstraintSet()
            constraintSet.clone(root)

            if (toolbarOpen) {
                (binding?.toolbar?.background as? MaterialShapeDrawable)?.interpolation = 1.0f
                constraintSet.constrainWidth(R.id.toolbar, if (isShort) 68.px else 120.px)
                constraintSet.clear(R.id.toolbar, ConstraintSet.END)

            } else {
                (binding?.toolbar?.background as? MaterialShapeDrawable)?.interpolation = 0.0f
                constraintSet.connect(R.id.toolbar, ConstraintSet.END, root.id, ConstraintSet.END)
                constraintSet.constrainWidth(R.id.toolbar, 0)

            }

            constraintSet.applyTo(root)

            if (toolbarOpen) {
                binding?.imgSearch?.visibility = View.GONE
                binding?.tvTitle?.visibility = View.GONE
                binding?.imgIcon?.visibility = if (isShort) View.GONE else View.VISIBLE
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding?.imgSearch?.visibility = View.VISIBLE
                    binding?.tvTitle?.visibility = View.VISIBLE
                }, 200L)
                binding?.imgIcon?.visibility = View.GONE
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        fragment.sharedElementEnterTransition = DetailsTransition()
        fragment.enterTransition = Fade()
        currentFragment?.exitTransition = Fade()
        fragment.sharedElementReturnTransition = DetailsTransition()

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        mainViewModel.sharedTransitionElements.forEach {
            fragmentTransaction.addSharedElement(it.first, it.second)
        }

        if (currentFragment != null) {
            fragmentTransaction.addToBackStack(fragment::class.java.canonicalName)
        }

        fragmentTransaction
            .replace(R.id.fragment_container, fragment)
            .setReorderingAllowed(true)
            .commit()
    }
}