package br.com.cinema.ui.single_movie_detail

import androidx.lifecycle.LiveData
import br.com.cinema.data.api.TheMovieDBInterface
import br.com.cinema.data.repository.MovieDetailsNetworkDataSource
import br.com.cinema.data.repository.NetworkState
import br.com.cinema.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable


class MovieDetailsRepository (private val apiService : TheMovieDBInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)
        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}