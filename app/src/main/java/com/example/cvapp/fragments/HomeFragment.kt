package com.example.cvapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.example.cvapp.R
import com.example.cvapp.WebViewActivity
import com.example.cvapp.common.Person
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private val KEY = "person"
    private lateinit var person: Person

    fun newInstance(person: Person): HomeFragment {
        val args = Bundle()
        val fragment = HomeFragment()
        args.putSerializable(KEY, person)
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        person = arguments?.getSerializable(KEY) as Person

        view.name.text = "${person.firstName} ${person.lastName}"
        view.position.text = person.profession
        view.about.text = person.about
        view.avatar.setImageResource(person.avatar)

        view.button_web.setOnClickListener {
//            startActivity(Intent(context, WebViewActivity::class.java).putExtra("web", person.webs))
            var uri = Uri.parse(person.webs)
            var viewIntent = Intent(Intent.ACTION_VIEW)
            viewIntent.data = uri
            startActivity(viewIntent)

        }

        view.button_resume.setOnClickListener {
            var uri = Uri.parse(person.contact.portfolio)
            var viewIntent = Intent(Intent.ACTION_VIEW)
            viewIntent.data = uri
            startActivity(viewIntent)
        }

        return view
    }
}