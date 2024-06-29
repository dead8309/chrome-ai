package org.example.chromeai.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface Message {
    @Serializable
    data class User(val text: String): Message
    @Serializable
    data class Bot(val text: String): Message
}