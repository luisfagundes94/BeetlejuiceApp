package com.luisfelipe.movie.di

import com.luisfelipe.movie.data.remote.repository_impl.MoviesRepositoryImpl
import com.luisfelipe.movie.data.remote.service.TheMovieDbService
import com.luisfelipe.movie.domain.repository.MoviesRepository
import com.luisfelipe.movie.domain.usecase.GetMovieDetailsFromApi
import com.luisfelipe.movie.domain.usecase.GetMovieGenresFromApi
import com.luisfelipe.movie.domain.usecase.GetSimilarMoviesFromApi
import com.luisfelipe.movie.presentation.details.DetailsViewModel
import com.luisfelipe.movie.presentation.details.SimilarMovieListAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.themoviedb.org/3/"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val movieModule = module {

    // ViewModels
    viewModel {
        DetailsViewModel(
            get<GetMovieDetailsFromApi>(),
            get<GetSimilarMoviesFromApi>(),
            get<GetMovieGenresFromApi>()
        )
    }

    // Adapters
    factory { SimilarMovieListAdapter() }

    // Usecases
    factory {
        GetMovieDetailsFromApi(get())
    }

    factory {
        GetSimilarMoviesFromApi(get())
    }

    factory {
        GetMovieGenresFromApi(get())
    }

    // Repositories
    factory {
        MoviesRepositoryImpl(
            get<TheMovieDbService>()
        ) as MoviesRepository
    }

    // Services
    factory {
        getTheMovieDbService(get())
    }

    // Retrofit
    single {
        createTheMovieDbRetrofit(get<OkHttpClient>())
    }

    factory {
        createOkHttpClient()
    }


}

private fun createTheMovieDbRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private fun createOkHttpClient(): OkHttpClient {
    val timeoutInSeconds = 10L
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
        .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

private fun getTheMovieDbService(retrofit: Retrofit) =
    retrofit.create(TheMovieDbService::class.java)