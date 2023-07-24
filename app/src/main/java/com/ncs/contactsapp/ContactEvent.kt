package com.ncs.contactsapp

sealed interface ContactEvent{
    object SaveContact:ContactEvent
    data class SetFname(val firstname:String):ContactEvent
    data class Setlname(val lastname:String):ContactEvent
    data class Setphnum(val pnumber:String):ContactEvent
    object showDialog:ContactEvent
    object closeDialog:ContactEvent
    data class sortContact(val sortType: SortType):ContactEvent
    data class deleteContact(val contacts: Contacts):ContactEvent
}