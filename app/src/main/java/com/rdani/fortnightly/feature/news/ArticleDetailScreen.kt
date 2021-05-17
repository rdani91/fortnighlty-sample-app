package com.rdani.fortnightly.feature.news

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.rdani.fortnightly.BaseFragment
import com.rdani.fortnightly.databinding.ScreenArticleDetailBinding
import com.rdani.fortnightly.model.Article

class ArticleDetailScreen : BaseFragment<ScreenArticleDetailBinding>() {

    companion object {
        private const val KEY_ARTICLE = "keyArticle"

        fun newInstance(article: Article): ArticleDetailScreen {
            return ArticleDetailScreen().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_ARTICLE, article)
                }
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ScreenArticleDetailBinding
        get() = ScreenArticleDetailBinding::inflate

    override fun initView() {

        val article: Article? = arguments?.getSerializable(KEY_ARTICLE) as? Article
        if (article == null) {
            activity?.onBackPressed()
            return
        }

        binding?.let {
            initArticle(it, article)
        }
    }

    private fun initArticle(detailBinding: ScreenArticleDetailBinding, article: Article) {

        ViewCompat.setTransitionName(detailBinding.imgArticle, "article_image")
        ViewCompat.setTransitionName(detailBinding.tvTitle, "article_title")

        detailBinding.tvTitle.text = article.title
        detailBinding.tvCategory.text = article.author?.uppercase()
        detailBinding.tvContent.text = article.content

        postponeEnterTransition()

        Glide
            .with(this)
            .load(article.urlToImage)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(detailBinding.imgArticle)
    }
}