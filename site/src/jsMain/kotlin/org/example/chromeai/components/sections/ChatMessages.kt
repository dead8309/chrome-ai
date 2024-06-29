package org.example.chromeai.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.silk.components.text.SpanText
import org.example.chromeai.model.Message

@Composable
fun ChatMessages(messages: List<Message>) {
    messages[0].let {
        when(it) {
            is Message.User -> UserMessage(it.text)
            is Message.Bot -> BotMessage(it.text)
        }
    }
}

@Composable
fun UserMessage(text: String) {
    SpanText(text)
}

//@Composable
//fun BotMessage(stream: ReadableStream<String>) {
//    var text by remember { mutableStateOf("") }
//    LaunchedEffect(stream) {
//        stream.getReader().readAsync().flatThen {
//            text = it.unsafeCast<String>()
//            stream.getReader().readAsync()
//        }
//    }
//}
@Composable
fun BotMessage(text: String) {
    SpanText(text)
}