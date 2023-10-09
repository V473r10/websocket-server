package com.codedicated

import com.codedicated.di.mainModule
import com.codedicated.plugins.*
import io.ktor.server.application.*
import org.koin.core.context.startKoin
import org.koin.ktor.ext.Koin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    startKoin{
        modules(mainModule)
    }
    configureSockets()
    configureRouting()
    configureSerialization()
    configureSecurity()
    configureMonitoring()
}
