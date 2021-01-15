package com.luisfelipe.base.presentation

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.luisfelipe.base.R
import com.luisfelipe.movie.R.navigation.movie_navigation

class BaseActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_base)

        Toast.makeText(this, "base activity", Toast.LENGTH_SHORT).show()
        setUpNavigation()
        setUpToolbar()
    }

    private fun setUpNavigation() {
        supportFragmentManager.findFragmentById(R.id.fragment_container_base_activity)?.let { fragment ->
            navigationController = fragment.findNavController().apply {
                val graph = navInflater.inflate(movie_navigation)
                setGraph(graph, intent.extras)
            }
        }
    }

    private fun setUpToolbar() {
        supportActionBar?.hide()
    }
}