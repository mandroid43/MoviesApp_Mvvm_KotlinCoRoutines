package com.manapps.mandroid.moviesapp_mvc_ret.ui.moviedetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.manapps.mandroid.moviesapp_mvc_ret.R
import com.manapps.mandroid.moviesapp_mvc_ret.databinding.ActivityMainBinding
import com.manapps.mandroid.moviesapp_mvc_ret.databinding.ActivityMovieDetailsBinding

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBindings()

    }
    private fun initBindings() {
        progressBar = binding.loadingPb
    }
}