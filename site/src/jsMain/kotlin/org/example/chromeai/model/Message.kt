package org.example.chromeai.model

import web.streams.ReadableStreamDefaultReader

sealed interface Message {
    data class User(val text: String) : Message
    sealed interface AI : Message {
        data class Text(val text: String) : AI
        data class Stream(val stream: ReadableStreamDefaultReader<String>) : AI
    }
}