package com.luisfelipe.base.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.luisfelipe.base.R

open class BaseActivity : AppCompatActivity() {

    private lateinit var navigationController: NavController
    protected open val navigationGraphId: Int by lazy { intent.getIntExtra(NAV_GRAPH_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        setUpNavigation()
        setUpToolbar()
    }

    private fun setUpNavigation() {
        supportFragmentManager.findFragmentById(R.id.fragment_container_base_activity)?.let { fragment ->
            navigationController = fragment.findNavController().apply {
                val graph = navInflater.inflate(navigationGraphId)
                setGraph(graph, intent.extras)
            }
        }
    }

    private fun setUpToolbar() {
        supportActionBar?.hide()
    }

    companion object {

        private const val NAV_GRAPH_ID = "NAV_GRAPH_ID"

        fun launch(
            context: Context,
            @NavigationRes graphResId: Int,
            block: (() -> Bundle)? = null
        ) {
            val bundle = (block?.invoke() ?: Bundle()).apply {
                putInt(NAV_GRAPH_ID, graphResId)
            }

            val intent = Intent(context, BaseActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}
