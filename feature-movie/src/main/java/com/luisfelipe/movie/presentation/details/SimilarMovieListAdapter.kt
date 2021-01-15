package com.luisfelipe.movie.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luisfelipe.movie.databinding.SimilarMovieItemBinding
import com.luisfelipe.movie.domain.model.SimilarMovie

class SimilarMovieListAdapter :
    RecyclerView.Adapter<SimilarMovieListAdapter.SimilarMovieListViewHolder>() {

    private val similarMovieList = mutableListOf<SimilarMovie>()

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
                itemBinding.releaseDate.text = similarMovie.release_date
                itemBinding.genres.text = similarMovie.genres.toString()
            }
    }
}