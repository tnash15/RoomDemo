package com.example.roomdemo.ui

import com.example.roomdemo.data.Person

interface HomeContract {

    interface View {
        fun showPeople(people: List<Person>)
        fun showError(message: String)
        fun showProgress()
        fun hideProgress()
        fun showPersonDetails(name: String, age: String, email: String, gender: String, country: String)
        fun onItemClicked(person: Person)
    }

    interface Presenter {
        fun getPeople()
        fun addPerson(name: String, age: String, email: String, gender: String, country: String)
        fun stop()
        fun onPersonSelected(person: Person)
    }
}