package com.luisfelipe.movie.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luisfelipe.extensions.load
import com.luisfelipe.extensions.setupScroll
import com.luisfelipe.extensions.toast
import com.luisfelipe.extensions.verticalRecyclerViewLayout
import com.luisfelipe.movie.R
import com.luisfelipe.movie.databinding.FragmentDetailsBinding
import com.luisfelipe.movie.domain.enums.ResultStatus
import com.luisfelipe.movie.domain.model.Movie
import org.koin.android.ext.android.inject

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by inject<DetailsViewModel>()
    private val similarMovieListAdapter by inject<SimilarMovieListAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initViewModelObservers()
        hideStaticIcons()

        viewModel.getMovieDetails()
        viewModel.getSimilarMovies()
    }

    private fun initRecyclerView() = with(binding.recyclerViewSimilarMovies) {
        val layoutManager = verticalRecyclerViewLayout()

        setHasFixedSize(true)
        this.layoutManager = layoutManager
        this.adapter = similarMovieListAdapter
        setupScroll(layoutManager) { viewModel.requestNextPage() }
    }

    private fun initViewModelObservers() {
        viewModel.apply {
            observeIsLoading()
            observeMovieDetails()
            observeSimilarMovies()
            observeIsFavoriteMovie()
        }
    }

    private fun DetailsViewModel.observeIsLoading() {
        isLoading.observe(viewLifecycleOwner, {
            when (it) {
                true -> {
                    hideStaticIcons()
                    showProgressBar()
                }
                false -> {
                    showStaticIcons()
                    hideProgressBar()
                }
            }
        })
    }

    private fun DetailsViewModel.observeMovieDetails() {
        movieDetailsResultStatus.observe(viewLifecycleOwner, { resultStatus ->
            when (resultStatus) {
                is ResultStatus.Success -> {
                    val movie = resultStatus.data
                    updateInitialFavoriteIconState(movie.id)
                    onFavoriteIconClick(movie)
                    setMovieInfo(movie)
                }
                is ResultStatus.Error -> toast(getString(R.string.warning_failed_to_fetch_movie_details))
            }
        })
    }

    private fun DetailsViewModel.observeSimilarMovies() {
        similarMoviesResultStatus.observe(viewLifecycleOwner, { resultStatus ->
            when (resultStatus) {
                is ResultStatus.Success -> similarMovieListAdapter.updateSimilarMovies(
                    resultStatus.data
                )
                is ResultStatus.Error ->  toast(getString(R.string.warning_failed_to_fetch_similar_movies))
            }
        })
    }

    private fun DetailsViewModel.observeIsFavoriteMovie() {
        isFavoriteMovie.observe(viewLifecycleOwner, {
            when (it) {
                true -> checkFavoriteIcon()
                else -> uncheckFavoriteIcon()
            }
        })
    }

    private fun onFavoriteIconClick(movie: Movie) {
        binding.imgFavoriteIcon.setOnClickListener {
            viewModel.updateFavoriteIconState(movie.id)
        }
    }

    private fun setMovieInfo(movie: Movie) {
        binding.backdrop.load(movie.backdrop)
        binding.title.text = movie.title
        binding.likes.text = getString(R.string.number_of_likes, movie.getFormattedLikes())
        binding.views.text = getString(R.string.number_of_views, movie.views)
    }

    private fun checkFavoriteIcon() =
        binding.imgFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)

    private fun uncheckFavoriteIcon() =
        binding.imgFavoriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun hideStaticIcons() {
        binding.imgFavoriteIcon.visibility = View.INVISIBLE
        binding.imgLikesIcon.visibility = View.INVISIBLE
        binding.imgViewsIcon.visibility = View.INVISIBLE
    }

    private fun showStaticIcons() {
        binding.imgFavoriteIcon.visibility = View.VISIBLE
        binding.imgLikesIcon.visibility = View.VISIBLE
        binding.imgViewsIcon.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
