package com.example.roomdemo.data

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface PersonDao{

    @get:Query("SELECT * FROM Person")
    val allPeople: Flowable<List<Person>>

    @Insert
    fun insert(vararg person: Person)

    @Update
    fun update(person: Person)

    @Delete
    fun delete(person: Person)

}