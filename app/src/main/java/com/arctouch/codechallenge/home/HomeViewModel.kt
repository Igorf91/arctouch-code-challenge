package com.arctouch.codechallenge.home

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val api: TmdbApi) : ViewModel() {

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>> = _upcomingMovies

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    @SuppressLint("CheckResult")
    fun fetchUpcomingMovies(page: Long = 1L) {
        api.upcomingMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val moviesWithGenres = it.results.map { movie ->
                    movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                }
                _upcomingMovies.postValue(moviesWithGenres)
            }, {
                _error.postValue(it)
            })
    }

    @SuppressLint("CheckResult")
    fun loadGenres() {
        api.genres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Cache.cacheGenres(it.genres)
            }
    }

}