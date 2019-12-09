package com.arctouch.codechallenge.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.RetrofitFactory.Companion.getApi
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.util.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.home_activity.progressBar
import kotlinx.android.synthetic.main.home_activity.recyclerView

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter
    private lateinit var linear: LinearLayoutManager

    private val api : TmdbApi by lazy {
        getApi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        adapter = HomeAdapter(arrayListOf())
        linear = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linear

        val scrollListener: EndlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener(linear) {
                override fun onLoadMore(
                    page: Int,
                    totalItemsCount: Int,
                    view: RecyclerView?
                ) {
                    viewModel.fetchUpcomingMovies((page + 1).toLong())
                }
            }
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders
            .of(this, HomeViewModelFactory(api))
            .get(HomeViewModel::class.java)

        viewModel.loadGenres()

        viewModel.upcomingMovies.observe(this, Observer {
            adapter.postValue(it.orEmpty())
            progressBar.visibility = View.GONE
        })

        viewModel.fetchUpcomingMovies()
    }
}
