package com.luisfelipe.base.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.luisfelipe.base.R

class BaseActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        setUpNavigation()
        setUpToolbar()
    }

    private fun setUpNavigation() {
        supportFragmentManager.findFragmentById(R.id.fragment_container_base_activity)?.let { fragment ->
            navigationController = fragment.findNavController().apply {
                val graph = navInflater.inflate(R.navigation.movie_navigation)
                setGraph(graph, intent.extras)
            }
        }
    }

    private fun setUpToolbar() {
        supportActionBar?.hide()
    }
}