package com.example.trendingPeople

import com.google.gson.annotations.SerializedName

/**
 * The Model for storing a single movie from the TMDb API
 *
 * SerializedName tags MUST match the JSON response for the
 * object to correctly parse with the gson library.
 */

data class TrendingPerson(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("knownForWork") val knownForWork: String?,
    @SerializedName("moviePoster") val moviePoster: String?,
    @SerializedName("movieDescription") val movieDescription: String?
)

