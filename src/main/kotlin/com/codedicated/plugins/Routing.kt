package com.codedicated.plugins

import com.codedicated.room.RoomController
import com.codedicated.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val roomController by inject<RoomController>( RoomController::class.java )
    install(Routing) {
        chatSocket(roomController)
        chatHistory(roomController)
    }
}
