package com.luisfelipe.movie.di

import com.luisfelipe.movie.data.remote.repository_impl.RepositoryImpl
import com.luisfelipe.movie.data.remote.service.TheMovieDbService
import com.luisfelipe.movie.domain.repository.Repository
import com.luisfelipe.movie.domain.usecase.GetMovieDetailsFromApi
import com.luisfelipe.movie.presentation.details.DetailsViewModel
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
            get<GetMovieDetailsFromApi>()
        )
    }

    // Usecases
    factory {
        GetMovieDetailsFromApi(get())
    }

    // Repositories
    factory {
        RepositoryImpl(
            get<TheMovieDbService>()
        ) as Repository
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