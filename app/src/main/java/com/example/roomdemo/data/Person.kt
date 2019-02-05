package com.example.roomdemo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Person{

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "personName")
    var name: String? = null

    @ColumnInfo(name = "personAge")
    var age: Int = 0

    @ColumnInfo(name = "personEmail")
    var email: String? = null

    @ColumnInfo(name = "gender")
    var gender: String? = null

    @ColumnInfo(name = "country")
    var country: String? = null
}
