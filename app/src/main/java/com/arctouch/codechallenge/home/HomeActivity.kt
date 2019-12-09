package com.arctouch.codechallenge.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.RetrofitFactory.Companion.getApi
import com.arctouch.codechallenge.api.TmdbApi
import kotlinx.android.synthetic.main.home_activity.progressBar
import kotlinx.android.synthetic.main.home_activity.recyclerView

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    private val api : TmdbApi by lazy {
        getApi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders
            .of(this, HomeViewModelFactory(api))
            .get(HomeViewModel::class.java)

        viewModel.loadGenres()

        viewModel.upcomingMovies.observe(this, Observer {
            recyclerView.adapter = HomeAdapter(it.orEmpty())
            progressBar.visibility = View.GONE
        })

        viewModel.fetchUpcomingMovies()
    }
}
