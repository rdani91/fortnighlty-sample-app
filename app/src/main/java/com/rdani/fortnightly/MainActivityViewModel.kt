package com.rdani.fortnightly

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rdani.fortnightly.model.Article

class MainActivityViewModel : ViewModel() {

    private var toolbarSizeMutableState: MutableLiveData<Boolean> = MutableLiveData(true)
    val toolbarSizeState: LiveData<Boolean> get() = toolbarSizeMutableState

    private var selectedArticleState: MutableLiveData<Article?> = MutableLiveData()
    val selectedArticle: LiveData<Article?> get() = selectedArticleState

    var sharedTransitionElements = arrayListOf<Pair<View, String>>()

    fun openToolbar() {
        if (toolbarSizeMutableState.value != true) {
            toolbarSizeMutableState.value = true
        }
    }

    fun closeToolbar() {
        if (toolbarSizeMutableState.value != false) {
            toolbarSizeMutableState.value = false
        }
    }

    fun onArticleSelected(article: Article) {
        selectedArticleState.value = article
    }
}