package com.example

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class Task(val id: Int, val content: String, val isDone: Boolean)

@Serializable
data class TaskRequest(val content: String, val isDone: Boolean)

object TaskRepository {
    private val tasks = mutableListOf<Task>(
        Task(id = 1, content = "Learn Ktor", isDone = true),
        Task(id = 2, content = "Build a REST API", isDone = false),
        Task(id = 3, content = "Write Unit Tests", isDone = false)
    )
fun getAll(): List<Task> = tasks
    fun getById(id: Int): Task? = tasks.find { it.id == id }
    
    fun add(task: Task): Task {
        tasks.add(task)
        return task
    }

    fun update(id: Int, updatedTask: Task): Boolean {
        val index = tasks.indexOfFirst { it.id == id }
        return if (index != -1) {
            tasks[index] = updatedTask.copy(id = id)
            true
        } else false
    }

    fun delete(id: Int): Boolean = tasks.removeIf { it.id == id }
}




fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello Gasidit Anuwong")
        }
        get("/tasks") {
            val tasks = TaskRepository.getAll()
            call.respond(tasks)
        }
    }
}
