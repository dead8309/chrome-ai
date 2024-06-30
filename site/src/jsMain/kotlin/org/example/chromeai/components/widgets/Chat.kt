@file:Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")

package org.example.chromeai.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.JustifyItems
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.text.SpanText
import js.promise.await
import kotlinx.coroutines.launch
import org.example.chromeai.components.Colors
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Span
import web.streams.ReadableStreamDefaultReader
import web.streams.ReadableStreamReadDoneResult
import web.streams.ReadableStreamReadValueResult
import kotlin.js.Date


@Composable
fun UserMessage(text: String) {
    val time = Date().toLocaleTimeString(
        "en-US",
        options = js("{hour: '2-digit', minute: '2-digit'}") as Date.LocaleOptions
    )
    SimpleGrid(
        numColumns = numColumns(1),
        modifier = Modifier.gap(4.px)
            .justifyItems(JustifyItems.End)
            .alignItems(AlignItems.FlexEnd)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.px),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpanText(
                "You",
                modifier = Modifier.fontWeight(FontWeight.Medium)
            )
            SpanText(
                time,
                modifier = Modifier.fontWeight(FontWeight.Medium)
            )
            Span(
                attrs = Modifier
                    .display(DisplayStyle.Flex)
                    .flexShrink(0)
                    .overflow(Overflow.Hidden)
                    .borderRadius(9999.px)
                    .size(32.px)
                    .toAttrs()
            ) {
                Image(
                    src = "/user.jpg",
                    alt = "User",
                    modifier = Modifier.aspectRatio(1f).fillMaxSize()
                )
            }
        }
        Column(
            modifier = Modifier
                .backgroundColor(Colors.PRIMARY)
                .color(Color.white)
                .borderRadius(0.5.cssRem)
                .padding(12.px)
        ) {
            SpanText(text)
        }
    }
}

@Composable
fun BotStreamResponse(readableStream: ReadableStreamDefaultReader<String>) {
    val time = Date().toLocaleTimeString(
        "en-US",
        options = js("{hour: '2-digit', minute: '2-digit'}") as Date.LocaleOptions
    )

    var text by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    scope.launch {
        while (true) {
            when (val result = readableStream.read().await()) {
                is ReadableStreamReadDoneResult -> break
                is ReadableStreamReadValueResult -> {
                    text = result.value
                }
            }
        }
    }
    BotMessage(text, time)
}

@Composable
fun BotTextResponse(text: String) {
    val time = Date().toLocaleTimeString(
        "en-US",
        options = js("{hour: '2-digit', minute: '2-digit'}") as Date.LocaleOptions
    )
    BotMessage(text, time)
}

@Composable
fun BotMessage(text: String, time: String) {
    SimpleGrid(
        numColumns = numColumns(1),
        modifier = Modifier.gap(8.px)
            .maxWidth(36.cssRem)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.px),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Span(
                attrs = Modifier
                    .display(DisplayStyle.Flex)
                    .flexShrink(0)
                    .overflow(Overflow.Hidden)
                    .borderRadius(9999.px)
                    .size(32.px)
                    .toAttrs()
            ) {
                Image(
                    src = "/ai.webp",
                    alt = "AI",
                    modifier = Modifier.aspectRatio(1f).fillMaxSize()
                )
            }
            SpanText(
                "Gemini",
                modifier = Modifier.fontWeight(FontWeight.Medium)
            )
            SpanText(
                time,
                modifier = Modifier.fontWeight(FontWeight.Medium)
            )
        }
        Column(
            modifier = Modifier
                .maxWidth(36.cssRem)
                .borderRadius(0.5.cssRem)
                .padding(12.px)
                .backgroundColor(Color.white)
        ) {
            SpanText(text)
        }
    }
}