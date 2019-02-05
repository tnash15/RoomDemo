package com.example.roomdemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.roomdemo.data.Person
import com.example.roomdemo.data.PersonDatabase
import com.example.roomdemo.ui.HomeContract
import com.example.roomdemo.ui.HomePresenter
import com.example.roomdemo.ui.PeopleAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_person.*

class MainActivity<onItemClickListener> : AppCompatActivity(), HomeContract.View {

    lateinit var homePresenter: HomeContract.Presenter
    private val peopleAdapter = PeopleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val personDatabase = Room.databaseBuilder<PersonDatabase>(applicationContext,
            PersonDatabase::class.java,"PersonDatabase").build()

        homePresenter = HomePresenter(personDatabase, this)

        homePresenter.getPeople()

        val linearLayoutManager = LinearLayoutManager(this)
        rvPeople.layoutManager = linearLayoutManager
        rvPeople.adapter = peopleAdapter

        btnSave.setOnClickListener {
            homePresenter.addPerson(name = etName.text.toString(), age = etAge.text.toString(),
                email = etEmail.text.toString() , gender = etGender.text.toString(), country = etCountry.text.toString())
        }

    }

    override fun showPeople(people: List<Person>) {
        peopleAdapter.setData(people)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPersonDetails (name: String, age: String, email: String, gender: String, country: String) {
        this.tvName.text = name
        this.tvAge.text = age
        this.tvEmail.text = email
        this.tvGender.text = gender
        this.tvCountry.text = country
    }

    override fun onStop() {
        super.onStop()
        homePresenter.stop()
    }

    override fun onItemClicked(person: Person){
        homePresenter.onPersonSelected(person)
    }
}
