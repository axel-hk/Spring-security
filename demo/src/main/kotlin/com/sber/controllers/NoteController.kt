package com.sber.controllers

import com.sber.config.PermissionService
import com.sber.domain.Note
import com.sber.services.NoteService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/app")
class NoteController(
    private val noteService: NoteService,
    private val permissionService: PermissionService
    ) {

    @GetMapping("/list")
    fun showList(model: Model): String {
        model.addAttribute("db", noteService.getList())
        return "viewList"
    }

    @GetMapping("/{id}/show")
    fun showPerson(@PathVariable("id") id: Long, model: Model): String {
        val result = noteService.getById(id)
        model.addAttribute("note", result)
        return "viewNote"
    }

    @GetMapping("/{id}/delete")
    fun deletePerson(@PathVariable("id") id: Long, model: Model): String {
        noteService.deleteById(id)
        model.addAttribute("db", noteService.getList())
        return "viewList"
    }

    @GetMapping("/add")
    fun addPerson(model: Model): String {
        model.addAttribute("note", Note())
        return "addNote"
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    fun addPerson(
        @ModelAttribute note: Note,
        authentication: Authentication,
        model: Model
    ): String {
        model.addAttribute("note", note)
        permissionService.addPermissionForAuthority(note, BasePermission.READ, authentication.name)
        noteService.addNote(note)
        return "redirect:/app/list"
    }

    @PostMapping("/edit")
    fun updatePerson(@ModelAttribute note: Note, model: Model): String {
        model.addAttribute("note", note)
        noteService.updateNote(note)
        return "redirect:/app/list"
    }

    @GetMapping("/{id}/edit")
    fun updatePerson(@PathVariable("id") id: Long, model: Model): String {
        val note = noteService.getById(id)
        model.addAttribute("note", note)
        return "editNote"
    }

    @GetMapping("/find")
    fun findNote(model: Model): String {
        model.addAttribute("note", Note())
        return "findNote"
    }

    @PostMapping("/find")
    fun findNote(@ModelAttribute note: Note, model: Model): String {
        model.addAttribute("db", noteService.findNotes(note))
        return "viewList"
    }

}