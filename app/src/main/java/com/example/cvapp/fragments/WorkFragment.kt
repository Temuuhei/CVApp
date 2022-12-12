package com.example.cvapp.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.example.cvapp.R
import com.example.cvapp.common.Education
import com.example.cvapp.common.FlowLayout
import com.example.cvapp.common.Person
import kotlinx.android.synthetic.main.fragment_work.view.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.android.synthetic.main.item_small.view.*

class WorkFragment : Fragment() {
    private val KEY = "person"
    private lateinit var person: Person

    fun newInstance(person: Person): WorkFragment {
        val args = Bundle()
        val fragment = WorkFragment()
        args.putSerializable(KEY, person)
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_work, container, false)
        person = arguments?.getSerializable(KEY) as Person

        val workParent = view.findViewById<LinearLayout>(R.id.works)
        val projectParent = view.findViewById<LinearLayout>(R.id.projects)
        val skillParent = view.findViewById<FlowLayout>(R.id.skills)

        for (wr in person.works) {
            val item = inflater.inflate(R.layout.item, container, false)
            item.title.text = wr.position
            item.place.text = wr.companyName
            item.year.text = wr.betweenYear
            workParent.addView(item)
        }
        for (pr in person.projects) {
            val item = inflater.inflate(R.layout.item, container, false)
            item.item_background.setBackgroundResource(R.drawable.item_back_green)
            item.title.text = pr.projectTitle
            item.place.text = pr.Contribution
            item.year.text = pr.betweenYear
            projectParent.addView(item)
        }

        renderSkills(inflater, container, skillParent)

        view.fab.setOnClickListener {
            showdialog(view, inflater, container, skillParent)
        }
        return view
    }

    private fun renderSkills(inflater: LayoutInflater, container: ViewGroup?, parent: FlowLayout) {
        parent.removeAllViews()
        for (sk in person.skills) {
            val item = inflater.inflate(R.layout.item_small, container, false)
            item.item_txtView.text = sk
            parent.addView(item)
        }
    }

    private fun showdialog(view: View, inflater: LayoutInflater, container: ViewGroup?, flowLayout: FlowLayout){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Add a skill")
        val input = EditText(context)
        input.hint = "Enter new skill"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            var newSkill = input.text.toString()
            person.add(newSkill)
            renderSkills(inflater, container, flowLayout)
            Snackbar.make(view, "Successfully added new skill", Snackbar.LENGTH_LONG)
                .setAction("Undo", View.OnClickListener {
                    person.removeLastSkill()
                    renderSkills(inflater, container, flowLayout)
                }).show()
        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }
}