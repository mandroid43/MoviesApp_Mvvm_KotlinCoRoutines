package com.manapps.mandroid.moviesapp_mvc_ret.ui.main

import android.content.Context
import android.text.SpannableString
import android.util.Log.*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manapps.mandroid.moviesapp_mvc_ret.R
import com.manapps.mandroid.moviesapp_mvc_ret.data.models.Movies
import com.manapps.mandroid.moviesapp_mvc_ret.databinding.MovieItemLayoutBinding
import com.manapps.mandroid.moviesapp_mvc_ret.utils.Constants
import com.manapps.mandroid.moviesapp_mvc_ret.utils.DateFormatter
import com.manapps.mandroid.moviesapp_mvc_ret.utils.DownloadImage


class MoviesAdapter(private val context: Context, private val moviesList: MutableList<Movies>) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder = MoviesViewHolder(
            MovieItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) = holder.bind(moviesList[position], context)

    // this method required non null value , how to always pass not null list ?
    override fun getItemCount(): Int = moviesList.size


    class MoviesViewHolder(val holderBinding: MovieItemLayoutBinding) :
        RecyclerView.ViewHolder(holderBinding.root) {
        fun bind(movie: Movies, context: Context) {

            try {
                holderBinding.movieTitleTv.text = movie.title
                holderBinding.releaseDateTv.text = DateFormatter.convertStringDate(
                    movie.releaseDate,
                    Constants.inputDateFormat,
                    Constants.outputDateFormat
                )
                var rating: Float? = movie.voteAverage?.toFloat() ?: 0f
                rating = rating!! / 2
                holderBinding.ratingBar.rating = rating
                val span = SpannableString(
                    context.resources.getString(
                        R.string.total_votes_count,
                        movie.voteCount.toString()
                    )
                )
                holderBinding.totalRatings.text = span
                DownloadImage.loadImage(holderBinding.moviePosterImgView, context, movie.posterPath)

                //how to handle exceptions here? if it fails at 4 than rest lines will not get executed
            } catch (exception: Exception) {
                d("Exception : ", exception.toString())
            }
        }
    }

    fun addUsers(moviesList: List<Movies>) {
        // is this method optimized ?
        this.moviesList.clear()
        this.moviesList.addAll(moviesList)

    }

}