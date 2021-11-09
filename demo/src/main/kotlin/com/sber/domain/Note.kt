package com.sber.domain

import javax.persistence.*

@Entity
@Table(name = "NOTE")
class Note(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val firstName: String = "",
    val lastName: String = "",
    val address:String = ""
) {
    override fun toString(): String {
        return "Note(id=$id, firstName='$firstName', lastName='$lastName', address='$address')"
    }

    fun contain(note: Note): Boolean {
        if (isNotContain(firstName, note.firstName)) return false
        if (isNotContain(lastName, note.lastName)) return false
        if (isNotContain(address, note.address)) return false
        return true
    }

    private fun isNotContain(s1: String?, s2: String?): Boolean {
        if (s1 != null && s2 != null) {
            if (!s1.trim().lowercase().contains(s2.trim().lowercase())) return true
        }
        return false
    }

    private fun isNotEqual(s1: String?, s2: String?): Boolean {
        if (!s1.isNullOrBlank() && !s2.isNullOrEmpty()) {
            if (s1.trim().lowercase() != s2.trim().lowercase()) return true
        }
        return false
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (isNotEqual(firstName, other.firstName)) return false
        if (isNotEqual(lastName, other.lastName)) return false
        if (isNotEqual(address, other.address)) return false

        return true
    }


}