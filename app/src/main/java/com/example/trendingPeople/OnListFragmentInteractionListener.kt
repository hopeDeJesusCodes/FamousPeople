package com.example.trendingPeople

/**
 * This interface is used by the [MovieInfo] to ensure
 * it has an appropriate Listener.
 *
 * In this app, it's implemented by [MoviesFragment]
 */
interface OnListFragmentInteractionListener {


    fun onItemClick(item: TrendingPerson)
}
