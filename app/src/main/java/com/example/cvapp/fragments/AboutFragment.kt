package com.example.cvapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.marginBottom
import com.example.cvapp.R
import com.example.cvapp.common.Education
import com.example.cvapp.common.FlowLayout
import com.example.cvapp.common.Person
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.item_small.view.*

class AboutFragment : Fragment() {
    private val KEY = "person"
    private lateinit var person: Person

    fun newInstance(person: Person): AboutFragment {
        val args = Bundle()
        val fragment = AboutFragment()
        args.putSerializable(KEY, person)
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        person = arguments?.getSerializable(KEY) as Person

        val eduParent = view.findViewById<LinearLayout>(R.id.educations)
        val weakParent = view.findViewById<FlowLayout>(R.id.weaknesses)
        val strParent = view.findViewById<FlowLayout>(R.id.strengths)

        for (edu in person.educations) {
            val item = inflater.inflate(R.layout.item, container, false)
            item.title.text= edu.title
            item.place.text= edu.school
            item.year.text= edu.betweenYear
            eduParent.addView(item)
        }

        for (weak in person.weaknesses) {
            val item = inflater.inflate(R.layout.item_small, container, false)
            item.item_txtView.text = weak
            item.item_txtView.setBackgroundResource(R.drawable.item_back_green)
            weakParent.addView(item)
        }

        for (str in person.strengths) {
            val item = inflater.inflate(R.layout.item_small, container, false)
            item.item_txtView.text = str
            strParent.addView(item)
        }

        return view
    }
}