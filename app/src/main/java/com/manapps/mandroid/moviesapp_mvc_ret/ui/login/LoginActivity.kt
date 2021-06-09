package com.manapps.mandroid.moviesapp_mvc_ret.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.google.android.material.textfield.TextInputLayout
import com.manapps.mandroid.moviesapp_mvc_ret.R
import com.manapps.mandroid.moviesapp_mvc_ret.databinding.ActivityLoginBinding
import com.manapps.mandroid.moviesapp_mvc_ret.databinding.ActivityMovieDetailsBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBindings()
    }
    private fun initBindings() {
        progressBar = binding.loadingPb
    }
}