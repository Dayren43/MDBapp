package com.ltu.m7019e.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ltu.m7019e.MovieDBApplication
import com.ltu.m7019e.viewmodel.MovieDBViewModel

class RefreshMovieCacheWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val type = inputData.getString("type")

        Log.d("RefreshMovieCacheWorker", "Refresh")
        when(type){
            "top_rated" -> {
                viewModel?.getTopRatedMovies()
            }
            "popular" -> {
                viewModel?.getPopularMovies()
            }
            else -> {return Result.failure()}
        }
        return Result.success()
    }

    companion object{
        private var viewModel: MovieDBViewModel? = null

        fun setViewModel(viewModel: MovieDBViewModel){
            this.viewModel = viewModel
        }
    }
}