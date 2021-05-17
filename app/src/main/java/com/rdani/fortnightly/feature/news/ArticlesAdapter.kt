package com.rdani.fortnightly.feature.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rdani.fortnightly.databinding.ListItemArticleBinding
import com.rdani.fortnightly.databinding.ListItemArticleFirstBinding
import com.rdani.fortnightly.model.Article
import java.util.*
import java.util.concurrent.TimeUnit

class ArticlesAdapter(private val onItemSelectedListener: (Article, View, View) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_FIRST = 1
        const val VIEW_TYPE_NORMAL = 2
    }

    private val articles: ArrayList<Article> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_FIRST -> {
                FirstArticleViewHolder(ListItemArticleFirstBinding.inflate(inflater, parent, false))
            }
            VIEW_TYPE_NORMAL -> {
                ArticleViewHolder(ListItemArticleBinding.inflate(inflater, parent, false))
            }
            else -> throw IllegalStateException("No viewHolder defined for $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = articles[position]
        if (holder is ArticleViewHolder) {
            updateNormalArticle(holder, article)
        } else if (holder is FirstArticleViewHolder) {
            updateFirstArticle(holder, article)
        }
    }

    override fun getItemCount(): Int = articles.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_FIRST
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    fun updateArticles(articles: List<Article>) {
        this.articles.clear()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    private fun updateFirstArticle(holder: FirstArticleViewHolder, article: Article) {
        holder.binding.tvTitle.text = article.title
        holder.binding.tvTime.text = getElapsedTime(article)

        Glide
            .with(holder.binding.root.context)
            .load(article.urlToImage)
            .into(holder.binding.imgArticle)

        holder.itemView.setOnClickListener {
            this.onItemSelectedListener(article, holder.binding.imgArticle, holder.binding.tvTitle)
        }

        ViewCompat.setTransitionName(holder.binding.imgArticle, "${article.hashCode()}-image")
        ViewCompat.setTransitionName(holder.binding.tvTitle, "${article.hashCode()}-title")
    }

    private fun updateNormalArticle(holder: ArticleViewHolder, article: Article) {
        holder.binding.tvTitle.text = article.title
        holder.binding.tvTime.text = getElapsedTime(article)

        Glide
            .with(holder.binding.root.context)
            .load(article.urlToImage)
            .into(holder.binding.imgArticle)

        holder.itemView.setOnClickListener {
            this.onItemSelectedListener(article, holder.binding.imgArticle, holder.binding.tvTitle)
        }

        ViewCompat.setTransitionName(holder.binding.imgArticle, "${article.hashCode()}-image")
        ViewCompat.setTransitionName(holder.binding.tvTitle, "${article.hashCode()}-title")
    }

    private fun getElapsedTime(article: Article): String {
        if (article.publishedAt == null) return ""

        val now = Date()
        val diffInMillis = now.time - article.publishedAt.time
        val days = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS)

        if (days > 1) {
            return "${days}D"
        }

        val minutes = TimeUnit.MINUTES.convert(diffInMillis, TimeUnit.MILLISECONDS)
        val hours = TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS)

        if (hours == 0L) {
            return "${minutes}m"
        }

        return "${hours}H"
    }
}

class ArticleViewHolder(val binding: ListItemArticleBinding) : RecyclerView.ViewHolder(binding.root)
class FirstArticleViewHolder(val binding: ListItemArticleFirstBinding) : RecyclerView.ViewHolder(binding.root)
