package com.ncs.contactsapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface ContactsDAO {

    @Upsert
    suspend fun upsert(contacts: Contacts)

    @Delete
    suspend fun delete(contacts: Contacts)

    @Query("Select  * from contacts ORDER BY firstname ASC ")
    fun sortbyfirstname(): Flow<List<Contacts>>

    @Query("Select  * from contacts ORDER BY lastname ASC ")
    fun sortbylastname(): Flow<List<Contacts>>

    @Query("Select  * from contacts ORDER BY phonenumber ASC ")
    fun sortbynumber(): Flow<List<Contacts>>
}