package com.ncs.contactsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel(private val dao: ContactsDAO):ViewModel() {

    private val _sortType= MutableStateFlow(SortType.FIRST_NAME)
    private val _contacts=_sortType.flatMapLatest {
        _sortType->
        when(_sortType){
            SortType.FIRST_NAME -> dao.sortbyfirstname()
            SortType.LAST_NAME -> dao.sortbylastname()
            SortType.PH_NUMBER -> dao.sortbynumber()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state= MutableStateFlow(ContactState())
    val state= combine(_state,_sortType,_contacts){state,sortType,contacts ->
        state.copy(
            contacts=contacts,
            sortType=sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())

    fun onEvent(event: ContactEvent){
        when(event){
            ContactEvent.SaveContact -> {
                val fname=state.value.firstname
                val lname=state.value.lastname
                val phnum=state.value.phonenumber

                if(fname.isBlank() || lname.isBlank() || phnum.isBlank()){
                    return
                }
                val contacts=Contacts(
                    firstname = fname,
                    lastname = lname,
                    phonenumber = phnum
                )
                viewModelScope.launch {
                    dao.upsert(contacts)
                }
                _state.update { it.copy(
                    isAdding = false,
                    firstname = "",
                    lastname = "",
                    phonenumber = "",
                ) }
            }
            is ContactEvent.SetFname -> {
                _state.update {
                    it.copy(firstname = event.firstname)
                }
            }
            is ContactEvent.Setlname -> {
                _state.update {
                    it.copy(lastname = event.lastname)
                }
            }
            is ContactEvent.Setphnum -> {
                _state.update {
                    it.copy(phonenumber = event.pnumber)
                }
            }
            ContactEvent.closeDialog -> {
                _state.update {
                    it.copy(isAdding = false)
                }
            }
            is ContactEvent.deleteContact -> {
                viewModelScope.launch {
                    dao.delete(event.contacts)
                }
            }

            ContactEvent.showDialog -> {
                _state.update {
                    it.copy(isAdding = true)
                }
            }
            is ContactEvent.sortContact -> {
                _sortType.value=event.sortType
            }
        }
    }

}