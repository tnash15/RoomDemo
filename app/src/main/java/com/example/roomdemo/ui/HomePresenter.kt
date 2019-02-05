package com.example.roomdemo.ui

import com.example.roomdemo.data.Person
import com.example.roomdemo.data.PersonDatabase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomePresenter(private val personDatabase: PersonDatabase, private val view: HomeContract.View) : HomeContract.Presenter{

   private val compositeDisposable: CompositeDisposable

    private var person: Person? = null

    private var isNewPerson = false

    init {
        compositeDisposable = CompositeDisposable()
    }

    override fun getPeople() {
        compositeDisposable.add(personDatabase.personDao().allPeople
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({people -> view.showPeople(people) }, { throwable -> view.showError(throwable.message!!) }))
    }

    override fun addPerson(name: String, age: String, email: String, gender: String, country: String) {
        if (person == null) {
            person = Person()
            isNewPerson = true
        }
        person!!.name = name
        person!!.age = Integer.parseInt(age)
        person!!.email = email
        person!!.gender = gender
        person!!.country = country
        compositeDisposable.add(
            Completable.fromCallable {
                if (isNewPerson) {
                    personDatabase.personDao().insert(person!!)
                } else {
                    personDatabase.personDao().update(person!!)
                }
            }.subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun onPersonSelected(person: Person) {
        this.person = person
        view.showPersonDetails(person.name!!, person.age.toString(), person.email!!, person.gender!!, person.country!!)

    }
}