package com.rdani.fortnightly.feature.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdani.fortnightly.BaseFragment
import com.rdani.fortnightly.MainActivityViewModel
import com.rdani.fortnightly.R
import com.rdani.fortnightly.databinding.ScreenArticleListBinding
import com.rdani.fortnightly.model.Article
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ArticleListScreen : BaseFragment<ScreenArticleListBinding>() {

    private val viewModel: ArticleListViewModel by inject()
    private val mainViewModel: MainActivityViewModel by sharedViewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ScreenArticleListBinding
        get() = ScreenArticleListBinding::inflate

    override fun initView() {
        initArticlesList()
        viewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        viewModel.articles.observe(viewLifecycleOwner, ::handleArticleUpdates)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadNews()
    }

    private fun initArticlesList() {
        binding?.swipeRefresh?.setOnRefreshListener {
            viewModel.loadNews(true)
        }

        postponeEnterTransition()

        (binding?.root as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }

        binding?.rvNews?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvNews?.adapter = ArticlesAdapter(::showDetailScreen)

        binding?.rvNews?.addOnScrollListener(onScrollListener())
    }

    private fun showDetailScreen(article: Article, image: View, titleView: View) {
        mainViewModel.sharedTransitionElements.clear()
        mainViewModel.sharedTransitionElements.add(Pair(image, "article_image"))
        mainViewModel.sharedTransitionElements.add(Pair(titleView, "article_title"))
        mainViewModel.onArticleSelected(article)
    }

    private fun onScrollListener() = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val scrollOffset: Int = recyclerView.computeVerticalScrollOffset()
            val toolbarHeight: Int = resources.getDimension(R.dimen.toolbar).toInt()
            if (toolbarHeight > scrollOffset) {
                mainViewModel.closeToolbar()
            } else {
                mainViewModel.openToolbar()
            }
        }
    }

    private fun handleArticleUpdates(articles: List<Article>) {
        binding?.swipeRefresh?.isRefreshing = false
        (binding?.rvNews?.adapter as? ArticlesAdapter)?.updateArticles(articles)
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            binding?.rvNews?.visibility = View.GONE
            binding?.loading?.visibility = View.VISIBLE
            binding?.loading?.startShimmer()
        } else {
            binding?.loading?.stopShimmer()
            binding?.rvNews?.visibility = View.VISIBLE
            binding?.loading?.visibility = View.GONE
        }
    }
}