package com.ncs.contactsapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contacts(
    val firstname:String,
    val lastname:String,
    val phonenumber:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)
