package com.sber.services

import com.sber.domain.Note
import com.sber.repositories.NoteRepository

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class NoteServiceTest {

    private val noteRepository = mockk<NoteRepository>()

    private val noteService: NoteService

    var db: List<Note>

    init {
        val note1 = Note(1,"Катя", "Иванова", "Москва")
        val note2 = Note(2,"Петя", "Петров", "Рязань")
        val note3 = Note(3,"Илья", "Васечкин", "Одинцово")
        val note4 = Note(4,"Маша", "Петрова", "Комсомольск-на-Амуре")
        val note5 = Note(5,"Саша", "Сидоров", "Лондон")
        val note6 = Note(6,"Маша", "Вас", "Нижний Новгород")

        db = listOf(note1, note2, note3, note4, note5, note6)

        noteService = NoteService(noteRepository)

        MockKAnnotations.init(this)
    }

    @Test
    fun getList() {
        // given
        every { noteRepository.findAll() } returns db
        val result: List<Note>

        // when
        result = noteService.getList()

        // then
        assertEquals(db.size, result.size)
    }

    @Test
    fun getById() {
        // given
        val id = 5L
        val note = Note(id,"Саша", "Сидоров", "Лондон")
        every { noteRepository.findById(id).get() } returns note

        // when
        val result = noteService.getById(5)

        // then
        assertEquals(note, result)
    }

    @Test
    fun addNote() {
        // given
        val note = Note(0,"Мария", "Кьюри", "Франция")
        every { noteRepository.save(note) } returns note


        // when
        val result = noteService.addNote(note)

        // then
        assertEquals(note, result)
    }

    @Test
    fun findById() {
        // given
        val id = 6L
        val note = Note(id,"Маша", "Вас", "Нижний Новгород")
        every { noteRepository.findById(id).get() } returns note

        // when
        val result = noteService.getById(6)

        // then
        assertEquals(note, result)
    }

    @Test
    fun updateNote() {
        // given
        val id = 6L
        val note = Note(6,"Петя", "Капица", "Лондон")
        every { noteRepository.findById(id).get() } returns note
        every { noteRepository.save(note) } returns note

        // when
        val result1 = noteService.updateNote(note)
        val result2 = noteService.getById(id)

        // then
        assertEquals(note, result1)
        assertEquals(note, result2)
    }

    @Test
    fun findNotes() {
        // given
        val noteToFind = Note(0L,"Маша", "", "")
        val expectedSetId: Set<Long> = setOf(4, 6)
        every { noteRepository.findAll() } returns db

        // when
        val result: Set<Long> = noteService
            .findNotes(noteToFind)
            .map { person -> person.id }.toSet()

        // then
        assertTrue(expectedSetId.containsAll(result))
    }
}