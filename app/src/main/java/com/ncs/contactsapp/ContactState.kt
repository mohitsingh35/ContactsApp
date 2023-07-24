package com.ncs.contactsapp

data class ContactState(
    val contacts:List<Contacts> = emptyList(),
    val firstname:String="",
    val lastname:String="",
    val phonenumber:String="",
    val isAdding:Boolean=false,
    val sortType: SortType=SortType.FIRST_NAME

)
