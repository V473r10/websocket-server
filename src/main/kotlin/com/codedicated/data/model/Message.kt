package com.codedicated.data.model
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Message(
    val text: String,
    val user: String,
    val datetime: String,
    @BsonId
    val id: String = ObjectId().toString(),
)
