package com.example.trendingPeople

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class PersonDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.person_detail)

        // Get the person's name and headshot URL from the Intent's extras
        val personName = intent.getStringExtra("person_name")
        val personHeadshotUrl = intent.getStringExtra("person_headshot")
        val personKnownForWork = intent.getStringExtra("knownForWork")
        val personKnownForWorkPosterUrl = intent.getStringExtra("moviePoster")
        val personMovieDescription = intent.getStringExtra("movieDescription")

        // Display the person's name on the page (e.g., using a TextView)
        val personNameTextView = findViewById<TextView>(R.id.personNameTextView)
        personNameTextView.text = personName

        // Load and display the person's headshot using Glide
        val personHeadshotImageView = findViewById<ImageView>(R.id.personHeadshot)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/$personHeadshotUrl")
            .centerInside()
            .placeholder(R.drawable.popcorn) // Add a placeholder image resource
            .into(personHeadshotImageView)

        // Load and display the person's best-known work's movie poster using Glide
        val moviePosterImageView = findViewById<ImageView>(R.id.movie_poster)
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/$personKnownForWorkPosterUrl") // Replace with the actual URL
            .centerInside()
            .placeholder(R.drawable.popcorn) // Add a placeholder image resource
            .into(moviePosterImageView)

        // Display the person's most known work in the famousTextView
        val famousTextView = findViewById<TextView>(R.id.famousTextView)
        famousTextView.text = "Most Known Work: $personKnownForWork"

        // Display the person's most known work in the famousTextView
        val movieTextView = findViewById<TextView>(R.id.movieDescription)
        movieTextView.text = "Most Known Work: $personMovieDescription"

    }
}
