package com.sber.domain

import javax.persistence.*

@Entity
@Table(name = "USER")
class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    val login: String = "",

    val password: String = "",

    val role: String = "",
)