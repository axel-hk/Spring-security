package com.sber

import com.sber.controllers.NoteController
import com.sber.domain.Note
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
internal class NoteControllerTest {

    @Autowired
    lateinit var context: WebApplicationContext

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var noteController: NoteController



    @BeforeEach
    fun setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(noteController).build()

        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .build()
    }

    @WithMockUser(value = "user")
    @Test
    @Throws(Exception::class)
    fun givenAuthRequest() {
        mockMvc.perform(get("/login"))
            .andExpect(status().isOk)
    }

    @WithMockUser(
        username = "user",
        authorities = ["ROLE_APP", "ROLE_ADMIN"]
    )
    @Test
    fun showList() {
        mockMvc.perform(get("/app/list"))
            .andExpect(status().isOk)
            .andExpect(view().name("viewList"))
    }

    @WithMockUser(
        username = "user",
        authorities = ["ROLE_APP", "ROLE_ADMIN"]
    )
    @Test
    fun showNote() {

        val id = 6
        val note = Note(6,"Мария", "Март", "Казань")

        mockMvc.perform(get("/app/$id/show"))
            .andExpect(status().isOk)
            .andExpect(view().name("viewNote"))
            .andExpect(model().attribute("note", note))
    }

    @WithMockUser(
        username = "user",
        authorities = ["ROLE_APP", "ROLE_ADMIN"]
    )
    @Test
    fun deleteNote() {
        val id = 1

        mockMvc.perform(get("/app/${id}/delete"))
            .andExpect(status().isOk)
            .andExpect(view().name("viewList"))
    }

    @WithMockUser(
        username = "user",
        authorities = ["ROLE_APP", "ROLE_ADMIN"]
    )
    @Test
    fun addNote() {
        mockMvc.perform(get("/app/add"))
            .andExpect(status().isOk)
            .andExpect(view().name("addNote"))
    }

    @WithMockUser(
        username = "user",
        authorities = ["ROLE_APP", "ROLE_ADMIN"]
    )
    @Test
    fun testAddNote() {
        mockMvc.perform(post("/app/add")
            .param("id", "7"))
            .andExpect(status().is3xxRedirection)
    }

    @WithMockUser(
        username = "user",
        authorities = ["ROLE_APP", "ROLE_ADMIN"]
    )
    @Test
    fun updatePerson() {
        val id = 6

        mockMvc.perform(get("/app/$id/edit"))
            .andExpect(status().isOk)
            .andExpect(view().name("editNote"))
    }

    @WithMockUser(
        username = "user",
        authorities = ["ROLE_APP", "ROLE_ADMIN"]
    )
    @Test
    fun testUpdateNote() {
        mockMvc.perform(post("/app/edit"))
    }

    @WithMockUser(
        username = "user",
        authorities = ["ROLE_APP", "ROLE_ADMIN"]
    )
    @Test
    fun findNote() {
        val note = Note(0,"Иван", "", "")

        mockMvc.perform(get("/app/find"))
            .andExpect(status().isOk)
            .andExpect(model().attribute("note", note))
    }

    @WithMockUser(
        username = "user",
        authorities = ["ROLE_APP", "ROLE_ADMIN"]
    )
    @Test
    fun testFindNote() {
        mockMvc.perform(post("/app/find"))
    }
}