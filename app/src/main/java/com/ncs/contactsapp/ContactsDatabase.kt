package com.ncs.contactsapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Contacts::class],
    version = 1
)
abstract class ContactDatabase: RoomDatabase() {

    abstract val dao: ContactsDAO
}