package com.luisfelipe.beetlejuiceapp.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.luisfelipe.base.presentation.BaseActivity
import com.luisfelipe.beetlejuiceapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
        startNextViewWithDelay()
    }


    private fun setupToolbar() {
        supportActionBar?.hide()
    }

    private fun startNextViewWithDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            BaseActivity.launch(this, R.navigation.movie_navigation)
        }, DELAY_TIME)
    }

    companion object {
        private const val DELAY_TIME = 2000L
    }
}