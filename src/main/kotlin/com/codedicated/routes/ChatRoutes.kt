package com.codedicated.routes

import com.codedicated.room.MemberAlreadyExistException
import com.codedicated.room.RoomController
import com.codedicated.session.ChatSession
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.chatSocket(roomController: RoomController) {
    webSocket("/chat-socket") {
        val session = call.sessions.get<ChatSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }
        try {
           roomController.onJoin(session.username, sessionId = session.sessionId, this)
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    val message = frame.readText()
                    roomController.sendMessage(session.username, message)
                }
            }
        } catch (e: MemberAlreadyExistException) {
            call.respond(Conflict)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            roomController.tryToLeave(session.username)
        }

    }
}

fun Route.chatHistory(roomController: RoomController) {
    get("/chat-history") {
        val messages = roomController.getAllMessages()
        call.respond(OK, messages)
    }
}