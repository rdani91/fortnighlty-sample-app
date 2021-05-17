package com.rdani.fortnightly.feature

import com.rdani.fortnightly.MainActivityViewModel
import com.rdani.fortnightly.feature.news.ArticleListViewModel
import com.rdani.fortnightly.services.scheduler.DefaultCoroutineScheduler
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {

    val module = module {

        val coroutineScheduler = DefaultCoroutineScheduler()

        viewModel { ArticleListViewModel(get(), coroutineScheduler) }
        viewModel { MainActivityViewModel() }
    }
}