package com.sber.repositories


import com.sber.domain.Note
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository: CrudRepository<Note, Long>