package com.sber.boot

import com.sber.config.PermissionService
import com.sber.domain.Address
import com.sber.domain.Note
import com.sber.repositories.NoteRepository
import com.sber.repositories.UserRepository
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class Boot (
    val noteRepository: NoteRepository,
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val permissionService: PermissionService
    ): ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {

        val user1 = Address(1, "Axel", passwordEncoder.encode("1111"), "ROLE_ADMIN")
        val user2 = Address(2, "Mary", passwordEncoder.encode("2222"), "ROLE_APP")
        val user3 = Address(3, "Max", passwordEncoder.encode("3333"), "ROLE_API")

        userRepository.saveAll(listOf(user1, user2, user3))

        val note1 = Note(1,"Иван", "Иванович", "Пенза")
        val note2 = Note(2,"Сергей", "Сергеевич", "Москва")
        val note3 = Note(3,"Александр", "Александрович", "Воронеж")
        val note4 = Note(4,"Владимир", "Владимирович", "Кострома")
        val note5 = Note(5,"Никита", "Андреевич", "Рязань")
        val note6 = Note(6,"Мария", "Март", "Казань")

        noteRepository.saveAll(listOf(note1, note2, note3, note4, note5, note6))

    }


}