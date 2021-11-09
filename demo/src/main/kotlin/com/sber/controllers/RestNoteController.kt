package com.sber.controllers

import com.sber.domain.Note
import com.sber.services.NoteService
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class RestNoteController(private val noteService: NoteService) {

    @GetMapping("/list")
    fun showList(model: Model) = noteService.getList()

    @GetMapping("/list/")
    fun findNote(@RequestParam("firstName") firstName: String,
                 @RequestParam("lastName") lastName: String,
                 @RequestParam("address") address: String)
            = noteService.findNotes(Note(0, firstName, lastName, address))


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun showPerson(@PathVariable("id") id: Long) = noteService.getById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addPerson(@RequestBody note: Note) = noteService.addNote(note)

    @DeleteMapping("/{id}")
    fun deletePerson(@PathVariable("id") id: Long) = noteService.deleteById(id)

    @PutMapping("/{id}")
    fun updatePerson(@PathVariable("id") id: Long, @RequestBody note: Note) =
        noteService.updateNote(id, note)

}