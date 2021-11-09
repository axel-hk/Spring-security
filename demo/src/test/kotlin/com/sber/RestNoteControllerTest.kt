package com.sber

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.sber.domain.Note
import com.sber.services.NoteService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.junit.jupiter.SpringExtension


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
internal class RestNoteControllerTest {

    @LocalServerPort
    private var port: Int = 0

    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var noteService: NoteService

    val mapper = jacksonObjectMapper()

    @Test
    fun givenAuthRequest() {
        val result: ResponseEntity<String> = restTemplate
            .withBasicAuth("any", "any")
            .getForEntity("/login", String::class.java)
        assertEquals(HttpStatus.OK, result.statusCode)
    }


    @Test
    fun showList() {

        val expected = noteService.getList()

        // админ
        val result = restTemplate
            .withBasicAuth("Axel", "1111")
            .exchange(
                url("/api/list"),
                HttpMethod.GET,
                HttpEntity(""),
                String::class.java
            )

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(mapper.writeValueAsString(expected), result.body)
    }

    @Test
    fun findNote() {

        val expected = listOf<Note>(
            Note(4,"Владимир", "Владимирович", "Кострома"),
             Note(6,"Мария", "Март", "Казань")
        )

        val result = restTemplate
            .withBasicAuth("Axel", "1111")
            .exchange(
                url("/api/list/?id=1fdsfsdf&firstName=маша&lastName=&address="),
                HttpMethod.GET,
                HttpEntity(""),
                String::class.java
            )

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(mapper.writeValueAsString(expected), result.body)
    }

    @Test
    fun showNote() {
        val id = 3
        val expected = Note(3,"Александр", "Александрович", "Воронеж")

        val result = restTemplate
            .withBasicAuth("Axel", "1111")
            .exchange(
                url("/api/$id"),
                HttpMethod.GET,
                HttpEntity(""),
                String::class.java
            )

        assertEquals(HttpStatus.FOUND, result.statusCode)
        assertEquals(mapper.writeValueAsString(expected), result.body)
    }

    @Test
    fun addNote() {
        val expected = Note(1,"Иван", "Иванович", "Пенза")

        val response = restTemplate
            .withBasicAuth("Axel", "1111")
            .postForEntity(url("/api"), HttpEntity(expected, HttpHeaders()), Note::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertTrue(response.body!! == expected)
    }

    @Test
    fun deleteNote() {
        val id = 3

        val result = restTemplate
            .withBasicAuth("Axel", "1111")
            .exchange(
                url("/api/$id"),
                HttpMethod.DELETE,
                null,
                Note::class.java
            )

        assertEquals(HttpStatus.OK, result.statusCode)
    }

    @Test
    fun updateNote() {
        val id = 3
        val expected = Note(3,"Александр", "Александрович", "Воронеж")

        val result = restTemplate
            .withBasicAuth("Axel", "1111")
            .exchange(
                url("/api/$id"),
                HttpMethod.PUT,
                HttpEntity(expected, HttpHeaders()),
                Note::class.java
            )

        assertEquals(expected, result.body)
        assertEquals(HttpStatus.OK, result.statusCode)
    }
}