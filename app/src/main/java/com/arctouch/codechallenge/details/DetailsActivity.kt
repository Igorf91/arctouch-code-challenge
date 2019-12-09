package com.arctouch.codechallenge.details

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.arctouch.codechallenge.util.formatDate
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.details_activity.backdropDetailImageView
import kotlinx.android.synthetic.main.details_activity.genresDetailTextView
import kotlinx.android.synthetic.main.details_activity.movieNameDetailTextView
import kotlinx.android.synthetic.main.details_activity.overviewMovieDetailTextView
import kotlinx.android.synthetic.main.details_activity.posterDetailImageView
import kotlinx.android.synthetic.main.details_activity.releaseDateDetailTextView

class DetailsActivity : AppCompatActivity() {

    private val movieImageUrlBuilder = MovieImageUrlBuilder()
    private lateinit var viewModel: DetailsViewModel

    companion object {
        private const val MOVIE_EXTRA = "MOVIE_CONTENT"

        operator fun invoke(context: Context, movie: Movie): Intent {
            return Intent(context, DetailsActivity::class.java).also {
                it.putExtra(MOVIE_EXTRA, movie)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        viewModel = ViewModelProviders
            .of(this, DetailsViewModelFactory(intent.getParcelableExtra(MOVIE_EXTRA)))
            .get(DetailsViewModel::class.java)

        loadContent()
    }

    private fun loadContent() {
        val movie = viewModel.getMovie()

        Glide.with(this)
            .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
            .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
            .into(posterDetailImageView)

        movieNameDetailTextView.text = movie.title
        overviewMovieDetailTextView.text = movie.overview
        releaseDateDetailTextView.text = movie.releaseDate?.formatDate()
        genresDetailTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }

        Glide.with(this)
            .load(movie.backdropPath?.let { movieImageUrlBuilder.buildBackdropUrl(it) })
            .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
            .into(backdropDetailImageView)
    }
}