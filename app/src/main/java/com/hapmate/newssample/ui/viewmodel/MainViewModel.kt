package com.hapmate.newssample.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hapmate.newssample.data.model.articles.Articles
import com.hapmate.newssample.data.repository.MainRepository
import com.hapmate.newssample.utils.Resource
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    lateinit var currentArticlesList: MutableList<Articles>

    sealed class ArticlesListEvent{
        class Success(val resultText: MutableList<Articles>): ArticlesListEvent()
        class Failure(val errorText: String) : ArticlesListEvent()
        object Loading : ArticlesListEvent()
        object Empty : ArticlesListEvent()
    }

    private val _articlesList = MutableStateFlow<ArticlesListEvent>(ArticlesListEvent.Empty)
    val articlesList: StateFlow<ArticlesListEvent> = _articlesList

    fun fetchArticles() {
        _articlesList.value = ArticlesListEvent.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val articlesListResponse = mainRepository.getArticles()) {
                is Resource.Error -> {
                    _articlesList.value = ArticlesListEvent.Failure("Incorrect")
                }
                is Resource.Success -> {
                    if (articlesListResponse.data == null) {
                        _articlesList.value = ArticlesListEvent.Failure("UnExpected Error")
                    } else {
                        _articlesList.value =
                            ArticlesListEvent.Success(articlesListResponse.data.articles)
                        currentArticlesList = articlesListResponse.data.articles
                    }
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getArticles(): MutableList<Articles>{
        return currentArticlesList
    }

}