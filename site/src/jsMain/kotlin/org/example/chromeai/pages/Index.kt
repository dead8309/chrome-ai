package org.example.chromeai.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.flex
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.common.PlaceholderColor
import org.example.chromeai.components.Colors
import org.example.chromeai.components.layouts.PageLayout
import org.example.chromeai.components.sections.ChatMessages
import org.example.chromeai.model.Message
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px


@Page
@Composable
fun HomePage() {
    val messages = remember { mutableStateListOf<Message>() }
    var prompt by remember { mutableStateOf("") }
    PageLayout("Home") {
        Column(
            modifier = Modifier.flex(1).overflow(overflowY = Overflow.Auto, overflowX = Overflow.Hidden),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.px)
        ) {
            ChatMessages(messages)
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.px),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Input(
                type = InputType.Text,
                value = prompt,
                onValueChange = {
                    prompt = it
                },
                placeholder = "Type a message...",
                placeholderColor = PlaceholderColor(Colors.MUTED_FOREGROUND)
            )

            Button(onClick = {
                messages.add(Message.User(prompt))
            }) {
                SpanText("Send")
            }
        }
    }
}
