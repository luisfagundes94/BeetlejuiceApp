package com.luisfelipe.movie.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luisfelipe.extensions.load
import com.luisfelipe.extensions.verticalRecyclerViewLayout
import com.luisfelipe.movie.R
import com.luisfelipe.movie.databinding.FragmentDetailsBinding
import com.luisfelipe.movie.domain.enums.ResultStatus
import com.luisfelipe.movie.domain.model.Movie
import org.koin.android.ext.android.inject

class DetailsFragment: Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by inject<DetailsViewModel>()

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

        //initRecyclerView()
        initViewModelObservers()

        viewModel.getMovieDetails()
    }

    private fun initRecyclerView() {
        binding.recyclerViewSimilarMovies.apply {
            setHasFixedSize(true)
            layoutManager = verticalRecyclerViewLayout()
        }
    }

    private fun initViewModelObservers() {
        viewModel.apply {
            movieDetailsResultStatus.observe(viewLifecycleOwner, { resultStatus ->
                when (resultStatus) {
                    is ResultStatus.Success -> setMovieInfo(resultStatus.data)
                    is ResultStatus.Error -> {}
                    else -> {}
                }
            })
        }
    }

    private fun setMovieInfo(movie: Movie) {
        binding.backdrop.load(movie.backdrop)
        binding.title.text = movie.title
        binding.likes.text = "${movie.likes} Likes"
        binding.views.text = "${movie.views} Views"
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
