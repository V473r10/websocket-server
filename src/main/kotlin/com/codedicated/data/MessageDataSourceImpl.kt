package com.codedicated.data

import com.codedicated.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase
class MessageDataSourceImpl(

    private val db: CoroutineDatabase
): MessageDataSource {

    private val messages = db.getCollection<Message>()
    override suspend fun getMessages(): List<Message> {
        return messages.find()
            .descendingSort(Message::datetime)
            .toList()
    }

    override suspend fun insertMessage(message: Message) {
        messages.insertOne(message)
    }
}