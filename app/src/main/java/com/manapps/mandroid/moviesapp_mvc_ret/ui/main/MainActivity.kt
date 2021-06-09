package com.manapps.mandroid.moviesapp_mvc_ret.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.harpercims.fsm.Utils.ResultStatus
import com.manapps.mandroid.moviesapp_mvc_ret.R
import com.manapps.mandroid.moviesapp_mvc_ret.data.api.RetrofitBuilder
import com.manapps.mandroid.moviesapp_mvc_ret.data.models.Movies
import com.manapps.mandroid.moviesapp_mvc_ret.databinding.ActivityMainBinding
import com.manapps.mandroid.moviesapp_mvc_ret.utils.CheckNetworkConnection
import com.manapps.mandroid.moviesapp_mvc_ret.utils.Utils


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // if any view is used more than 1 time than taking it's reference to its parent class otherwise use binding.view :ok?
    private lateinit var progressBar: ProgressBar
    private lateinit var errorView: MaterialTextView
    private lateinit var moviesRv: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var viewModel: MoviesViewModel
    private var moviesList: MutableList<Movies> = mutableListOf()

    // is storing apiKey in strings file a good idea ? ok : alternative
    // is providing apiKey from here recommended
    private lateinit var apiKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        apiKey = resources.getString(R.string.API_KEY)
        //getIntentData()
        initBindings()
        setupUI()
        setUpViewModel()
        setUpObservers()
        loadData()
        //doing 6 sync calls on main thread will result in app doing too much on main thread. How to deal this?
        // is this order correct?
    }

    private fun initBindings() {
        moviesRv = binding.moviesRv
        errorView = binding.errorView
        progressBar = binding.progressBar
    }
    private fun loadData() {
        if (CheckNetworkConnection.isOnline(this)) {
            fetchMovies()
        } else {
            setProgressBarGone()
            moviesRv.isVisible = false
            errorView.isVisible = true
            errorView.text = resources.getString(R.string.no_network_connected)
        }
    }

    private fun setupUI() {
        moviesRv.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        moviesAdapter = MoviesAdapter(this, moviesList)
        moviesRv.adapter = moviesAdapter
        moviesAdapter.notifyDataSetChanged()


    }

    private fun setUpViewModel() {
        viewModel =
            ViewModelProviders.of(this, MoviesViewModelFactory(RetrofitBuilder.apiService, this))
                .get(MoviesViewModel::class.java)
    }

    private fun fetchMovies() {
        setProgressBarVisible()
        viewModel.sendGetMoviesListRequest(apiKey)
    }

    private fun setUpObservers() {
        // in setUpObservers()  we should observe livedata(moviesLiveData) or function(fetchUsers)



        // we could have made 2 observers for like below
        // but with this we have to make 2 observers sucesslivedata and errorlivedata for n api calls
//        viewModel.moviesLiveData.observe(this, {
//            setUpMoviesView(it)
//        })
//        viewModel.errorLiveData.observe(this, {
//            Utils.showMessage(this,it)
//            setUpMoviesView(null)
//        })


        // here final output is with the help of 2 helpers ; Resource and ResultWrapper ;; how to improve it and reduce it to 1?
        viewModel.moviesLiveData.observe(this, {
            it?.let {
                when (it.status) {
                    ResultStatus.SUCCESS -> {
                        setProgressBarGone()
                        setUpMoviesView(it.data?.results)
                    }
                    ResultStatus.ERROR -> {
                        setProgressBarGone()
                        Utils.showMessage(this, it.message!!)
                    }
                    ResultStatus.LOADING -> {
                        setProgressBarGone()
                    }
                }
            }
        })
    }

    private fun setUpMoviesView(moviesList: List<Movies>?) {
        if (!moviesList.isNullOrEmpty()) {
            setUpMoviesRecyclerView(moviesList)
        } else {
            setUpNoMoviesView()
        }
    }

    private fun setUpMoviesRecyclerView(moviesList: List<Movies>?) {
        errorView.isVisible = false
        moviesRv.isVisible = true
        retrieveList(moviesList)
    }

    private fun setUpNoMoviesView() {
        moviesRv.isVisible = false
        errorView.isVisible = true
        errorView.text=resources.getString(R.string.error_msg)
    }

    private fun setProgressBarVisible() {
        progressBar.isVisible = true
    }

    private fun setProgressBarGone() {
        progressBar.isVisible = false
    }

    private fun retrieveList(movies: List<Movies>?) {
        moviesAdapter.addUsers(movies!!)
        moviesAdapter.notifyDataSetChanged()
    }


}

/* (1)handle exception handling
* (2)security checks
* (3)where to store api key/accessTokens
* (4)where to store Constants Urls
* (5)image loading tips
* (6)improving tips?
*
*
*
* Scenerio 1:
* When clicked on itemview go to next screen and
* update its value
* now when coming back
* we need to show the updated value in recyclerview
* so
* Case1: calling api on resume of activity?
* Case2: go to next screen with activity results
* and when coming back manually update the values of that particular item
* with the help of storing clicked item's position ?
*
* or some other way?
*
*
* Scenerio2:
* How to decide is the approach you are thinking about is right or not!
*
*how to make layouts for all screen's
*
*  */