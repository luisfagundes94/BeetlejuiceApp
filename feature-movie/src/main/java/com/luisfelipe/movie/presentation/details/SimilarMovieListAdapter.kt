package com.luisfelipe.movie.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luisfelipe.extensions.load
import com.luisfelipe.movie.databinding.SimilarMovieItemBinding
import com.luisfelipe.movie.domain.model.Genre
import com.luisfelipe.movie.domain.model.SimilarMovie

class SimilarMovieListAdapter :
    RecyclerView.Adapter<SimilarMovieListAdapter.SimilarMovieListViewHolder>() {

    private val similarMovieList = mutableListOf<SimilarMovie>()

    fun updateSimilarMovies(similarMovies: List<SimilarMovie>) {
        this.similarMovieList.addAll(similarMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieListViewHolder {
        val itemBinding =
            SimilarMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarMovieListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SimilarMovieListViewHolder, position: Int) =
        holder.bind(similarMovieList[position])

    override fun getItemCount() = similarMovieList.size

    inner class SimilarMovieListViewHolder(private val itemBinding: SimilarMovieItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(similarMovie: SimilarMovie) {
            itemBinding.title.text = similarMovie.title
            itemBinding.releaseDate.text = similarMovie.getFormattedYear()
            itemBinding.poster.load(similarMovie.poster)
            itemBinding.genres.text = similarMovie.getFormattedGenreNames()
        }
    }
}