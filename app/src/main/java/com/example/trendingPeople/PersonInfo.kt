package com.example.trendingPeople

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * [RecyclerView.Adapter] that can display a list of [TrendingPerson] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class PersonInfo(
    private val people: List<TrendingPerson>,
    private val pListener: OnListFragmentInteractionListener
) : RecyclerView.Adapter<PersonInfo.PersonInfoHolder>() {


    inner class PersonInfoHolder(val pView: View) : RecyclerView.ViewHolder(pView) {
        val pName: TextView = pView.findViewById(R.id.person_name)
        val pHeadshot: ImageView = pView.findViewById(R.id.headshot)

        init {
            pView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val person = people[position]

                    // Create an Intent to open PersonDetailActivity
                    val intent = Intent(pView.context, PersonDetailActivity::class.java)

                    // Pass the person's info as an extra
                    intent.putExtra("person_name", person.name)
                    intent.putExtra("person_headshot", person.profilePath)
                    intent.putExtra("knownForWork", person.knownForWork)
                    intent.putExtra("moviePoster", person.moviePoster)
                    intent.putExtra("movieDescription", person.movieDescription)
                    pView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonInfoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person, parent, false) // Use the correct layout
        return PersonInfoHolder(view)
    }
    override fun onBindViewHolder(holder: PersonInfoHolder, position: Int) {
        val person = people[position]

        holder.pName.text = person.name

        Glide.with(holder.pView)
            .load("https://image.tmdb.org/t/p/w500/${person.profilePath}")
            .centerInside()
            .into(holder.pHeadshot)

    }


    override fun getItemCount(): Int {
        return people.size
    }
}

