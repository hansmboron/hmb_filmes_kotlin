package br.com.cinema.ui.single_movie_detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import br.com.cinema.R
import br.com.cinema.data.api.POSTER_BASE_URL
import br.com.cinema.data.api.TheMovieDBClient
import br.com.cinema.data.api.TheMovieDBInterface
import br.com.cinema.data.repository.NetworkState
import br.com.cinema.data.vo.MovieDetails
import br.com.cinema.ui.buy_ticket.BuyActivity
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository
    private val TAG = "coisa loca";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id",1)
        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        val action = supportActionBar
        action!!.title = "Detalhes do Filme"
        action.setDisplayHomeAsUpEnabled(true)
        movieRepository = MovieDetailsRepository(apiService)
        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            findViewById<ProgressBar>(R.id.progress_bar).visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            findViewById<TextView>(R.id.txt_error).visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val title = findViewById<TextView>(R.id.movie_title).text
        when(item.itemId) {
            R.id.nav_favorite -> Toast.makeText( this,"Adicionado aos favoritos", Toast.LENGTH_SHORT).show()
            R.id.nav_buy -> { val intent = Intent(this, BuyActivity::class.java)
            intent.putExtra("title", title)
            this.startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun bindUI( it: MovieDetails){
        val action = supportActionBar
        action!!.title = it.title
        findViewById<TextView>(R.id.movie_title).text = it.title
        findViewById<TextView>(R.id.movie_tagline).text = it.tagline
        findViewById<TextView>(R.id.movie_release_date).text = it.release_date
        findViewById<TextView>(R.id.movie_rating).text = it.vote_average.toString()
        findViewById<TextView>(R.id.movie_runtime).text = it.runtime.toString() + " minutos"
        findViewById<TextView>(R.id.movie_overview).text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        findViewById<TextView>(R.id.movie_budget).text = formatCurrency.format(it.budget)
        findViewById<TextView>(R.id.movie_revenue).text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.poster_path
        val iv_movie_poster : ImageView = findViewById<ImageView>(R.id.iv_movie_poster)
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);
    }

    private fun getViewModel(movieId:Int): SingleMovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository,movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}