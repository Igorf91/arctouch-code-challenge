package com.arctouch.codechallenge.details

import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.model.Movie

class DetailsViewModel(private val movie: Movie) : ViewModel() {

    fun getMovie() = movie
}