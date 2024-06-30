package org.example.chromeai.components.sections

import androidx.compose.runtime.Composable
import org.example.chromeai.components.widgets.BotStreamResponse
import org.example.chromeai.components.widgets.BotTextResponse
import org.example.chromeai.components.widgets.UserMessage
import org.example.chromeai.model.Message

@Composable
fun ChatMessages(messages: List<Message>) {
    messages.forEach {
        when (it) {
            is Message.User -> UserMessage(it.text)
            is Message.AI.Text -> BotTextResponse(it.text)
            is Message.AI.Stream -> BotStreamResponse(it.stream)
        }
    }
}
