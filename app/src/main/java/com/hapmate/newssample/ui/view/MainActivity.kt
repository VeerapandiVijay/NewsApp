package com.hapmate.newssample.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hapmate.newssample.R
import com.hapmate.newssample.data.api.ApiHelper
import com.hapmate.newssample.data.model.articles.Articles
import com.hapmate.newssample.ui.base.ViewModelFactory
import com.hapmate.newssample.ui.view.adapter.ArticlesListAdapter
import com.hapmate.newssample.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ArticlesListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        setupObserver()
        mainViewModel.fetchArticles()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ArticlesListAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper().getApiService())
        ).get(MainViewModel::class.java)
    }

    private fun setupObserver() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.articlesList.collect { event ->
                when (event) {
                    is MainViewModel.ArticlesListEvent.Success -> {
                        progressBar.visibility = View.GONE
                        renderList(event.resultText)
                    }
                    is MainViewModel.ArticlesListEvent.Failure -> {
                        progressBar.visibility = View.GONE
                    }
                    is MainViewModel.ArticlesListEvent.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun renderList(articles: List<Articles>) {
        adapter.addData(articles)
    }
}