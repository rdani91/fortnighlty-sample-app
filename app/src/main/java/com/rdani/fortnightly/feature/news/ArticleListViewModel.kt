package com.rdani.fortnightly.feature.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rdani.fortnightly.model.Article
import com.rdani.fortnightly.repository.article.ArticleRepository
import com.rdani.fortnightly.services.scheduler.CoroutineScheduler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleListViewModel(
    private val articleRepository: ArticleRepository,
    private val coroutineScheduler: CoroutineScheduler,
) : ViewModel() {


    private var loadingState: MutableLiveData<Boolean> = MutableLiveData(true)
    val loading: LiveData<Boolean> get() = loadingState

    private var articlesState: MutableLiveData<List<Article>> = MutableLiveData(arrayListOf())
    val articles: LiveData<List<Article>> get() = articlesState

    fun loadNews(forced: Boolean = false) {
        loadingState.value = true

        viewModelScope.launch {
            val result = withContext(coroutineScheduler.background()) {
                articleRepository.loadArticles(forced)
            }

            loadingState.value = false
            if (result.isNullOrEmpty()) {
                //TODO error?
            } else {
                articlesState.value = result
            }
        }
    }

}