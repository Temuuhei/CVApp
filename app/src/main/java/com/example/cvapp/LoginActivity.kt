package com.example.cvapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cvapp.common.Person
import kotlinx.android.synthetic.main.activity_login.*
import com.example.cvapp.common.*

class LoginActivity : AppCompatActivity() {
    val KEY = "PERSON"
    val PREF = "CVPREF"
    var users = ArrayList<Person>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        createPerson()

        val prefs = getSharedPreferences(PREF, MODE_PRIVATE)

        if (prefs.getBoolean("auth", false)) {
            val email = prefs.getString("email", "")
            if (email != null) {
                val foundUser = findUserByEmail(email)
                if (foundUser != null) {
                    startMainActivity(foundUser)
                }
            }
        }

        login.setOnClickListener {
            val inputEmail = et_email.text.toString()
            if (isValidated(inputEmail, et_password.text.toString())) {
                val editor = prefs.edit()

                editor.putBoolean("auth", true)
                editor.putString("email", inputEmail)
                editor.apply()

                val foundUser = findUserByEmail(inputEmail)
                if (foundUser != null) {
                    startMainActivity(foundUser)
                }


            } else {
                Toast.makeText(applicationContext, "Wrong credential", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startMainActivity(person: Person) {
        var mainIntent = Intent(applicationContext, MainActivity::class.java)

        mainIntent.putExtra(KEY, person)
        startActivity(mainIntent)
    }

    private fun isValidated(email: String, password: String): Boolean {
        for (user in users) {
            if (user.contact.email == email && user.password == password) {
                return true
            }
        }
        return false
    }

    private fun findUserByEmail(email: String): Person? {
        for (user in users) {
            if (user.contact.email == email) {
                println(user.contact.email)
                return user
            }
        }
        return null
    }

    fun createPerson() {
        //---------------------------------Contact---------------------------------//
        var contact1: Contact = Contact(
            "+1 (312) 647-4635",
            "ttsogt@miu.edu.mn",
            "Temuuhei",
            "tr1um",
            "https://github.com/temuuhei",
            "tmk_tr1um",
            "https://temuujin.tk"
        )

        var education1: Education = Education(
            "Bachelor of Computer Science",
            "2008-2012",
            "National University of Mongolia"
        )
        var education1_1: Education = Education("Master of Computer Science", "2021-2024", "MIU")

        var project1: Project = Project("2016-2017","Shoe Gallery", "Backend Developer")
        var project1_1: Project = Project("2017-2019","MAK ERP", "Full Stack Engineer")

        var work1: Work = Work("2016-2017", "Asterisk-Technologies LLC","Developer")
        var work2: Work = Work("2017-2021", "Mongolyn Alt (MAK) LLC","Senior Software Developer")

        var person1Strength: ArrayList<String> = arrayListOf("Confident", "Creative", "Dedicated", "Determined", "Decisive")
        var person1Weaknesses: ArrayList<String> = arrayListOf("Impatient", "Allows emotions to show", "Close-minded", "Perfectionist", "Likes to take risks")
        var person1Skills: ArrayList<String> = arrayListOf("Flexibility", "Java", "Python", "Odoo", "NodeJs", "AWS")
        var person1:Person = Person(R.drawable.ava_temka, "Temuujin", "Tsogt", "temka", "Back-End Developer", "I'm software engineer with around 6 years of experience. My main programming languages are Java and Python. \n", "https://www.temuujin.tk/", person1Strength, person1Weaknesses, person1Skills, arrayListOf(education1, education1_1),contact1, arrayListOf(project1,project1_1), arrayListOf(work1, work2))

        users.add(person1)

    }
}