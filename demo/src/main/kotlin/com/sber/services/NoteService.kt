package com.sber.services

import com.sber.domain.Note
import com.sber.repositories.NoteRepository
import org.springframework.stereotype.Service

@Service
class NoteService(private val repository: NoteRepository) {

    fun getList(): List<Note> = repository.findAll().toList()

    fun getById(id: Long) = repository.findById(id).get()

    fun addNote(note: Note): Note = repository.save(note)

    fun deleteById(id: Long) {
        repository.delete(repository.findById(id).get())
    }

    fun updateNote(note: Note): Note = repository.save(note)

    fun updateNote(id: Long, note: Note): Note {
        val noteToUpdate = Note (
            id,
            note.firstName,
            note.lastName,
            note.address
        )
        return repository.save(noteToUpdate)
    }

    fun findNotes(note: Note): List<Note> = repository.findAll()
        .filter { item -> item.contain(note) }
}