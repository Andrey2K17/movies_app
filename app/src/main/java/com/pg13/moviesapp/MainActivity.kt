package com.pg13.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pg13.domain.entities.Resource
import com.pg13.moviesapp.ui.films.FilmsViewModel
import com.pg13.moviesapp.utils.launchOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel: FilmsViewModel by viewModels()
        viewModel.getTopFilms()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.films.collect { res ->
                    when (res) {
                        is Resource.Error -> {
                            Log.d("test123", "error: ${res.message}")
                        }

                        is Resource.Loading -> Log.d("test123", "loading")
                        is Resource.Success -> Log.d("test123", "success: ${res.data}")
                    }

                }
            }
        }
    }
}