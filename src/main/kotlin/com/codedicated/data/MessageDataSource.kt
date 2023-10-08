package com.codedicated.data

import com.codedicated.data.model.Message

interface MessageDataSource {
    suspend fun getMessages(): List<Message>

    suspend fun insertMessage(message: Message)
}