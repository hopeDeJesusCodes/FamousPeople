package com.example.trendingPeople

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import okhttp3.Headers
import org.json.JSONObject

// --------------------------------//
// CHANGE THIS TO BE YOUR API KEY  //
// --------------------------------//
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/trending/person/day"

class PersonFragment : Fragment(), OnListFragmentInteractionListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_scroll, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        // Using the client, perform the HTTP request
        client[TMDB_BASE_URL, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
            ) {
                try {
                    val persons = parseTmdbResponse(json)
                    recyclerView.adapter = PersonInfo(persons, this@PersonFragment)
                    progressBar.hide()
                } catch (e: Exception) {
                    Log.e("PersonFragment", "Error parsing JSON: ${e.message}")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                progressBar.hide()
                t?.message?.let {
                    Log.e("PersonFragment", "HTTP Request Failed: $errorResponse")
                    Log.e("PersonFragment", "Error Message: ${t.message}")
                }
            }
        }]
    }


    override fun onItemClick(item: TrendingPerson) {
        Toast.makeText(context, "Person: ${item.name}", Toast.LENGTH_LONG).show()
    }

    data class TmdbResponse(
        val results: List<TrendingPerson>
    )

    private fun parseTmdbResponse(json: JsonHttpResponseHandler.JSON): List<TrendingPerson> {
        val people = mutableListOf<TrendingPerson>()

        try {
            val jsonString = json.toString() // Convert the JSON response to a string

            // Check if the string starts with "jsonObject="
            if (jsonString.startsWith("jsonObject=")) {
                // Remove the prefix to get the JSON data
                val jsonData = jsonString.substring("jsonObject=".length)

                // Create a JSON object from the JSON data
                val jsonObject = JSONObject(jsonData)

                // Extract the "results" array
                val resultsArray = jsonObject.getJSONArray("results")

                val gson = Gson()

                for (i in 0 until resultsArray.length()) {
                    val movieResponse = resultsArray.getJSONObject(i)
                    val name = movieResponse.getString("name")
                    val headshot = movieResponse.getString("profile_path")
                    // Extract the "known_for" array
                    val knownForArray = movieResponse.getJSONArray("known_for")
                    // Extract the title of the first item in the "known_for" array as most known work
                    val knownForWork = knownForArray.optJSONObject(0)?.getString("title") ?: ""
                    val moviePoster = knownForArray.optJSONObject(0)?.getString("poster_path") ?: ""
                    val movieDescription = knownForArray.optJSONObject(0)?.getString("overview") ?: ""

                    // Create a TrendingPerson object for this person
                    val trendingPerson = TrendingPerson(
                        id = 0,
                        name = name,
                        profilePath = headshot,
                        knownForWork = knownForWork,
                        moviePoster = moviePoster,
                        movieDescription = movieDescription
                    )


                    // Add the InTheatersMovie object to the list
                    people.add(trendingPerson)
                    Log.d("PersonFragment", "Person: $name, Known For: $moviePoster")

                }

                Log.d("PersonFragment", "Number of movies: ${people.size}")
                Log.d("PersonFragment", "$jsonString")
            } else {
                Log.e("PersonFragment", "Invalid JSON format: $jsonString")
            }
        } catch (e: Exception) {
            Log.e("PersonFragment", "Error parsing JSON: ${e.message}")
            Log.e("PersonFragment", "JSON Response: ${json.toString()}")
        }

        // Return the list of InTheatersMovie objects
        return people
    }


}
