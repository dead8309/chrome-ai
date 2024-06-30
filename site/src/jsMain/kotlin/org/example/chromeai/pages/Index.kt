package org.example.chromeai.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.icons.fa.FaPaperPlane
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.style.toModifier
import js.promise.await
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.example.chromeai.ai.ChromeAiSession
import org.example.chromeai.ai.ai
import org.example.chromeai.components.Colors
import org.example.chromeai.components.layouts.PageLayout
import org.example.chromeai.components.sections.ChatMessages
import org.example.chromeai.model.Message
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.dom.TextArea

@Page
@Composable
fun HomePage() {
    val messages = remember { mutableStateListOf<Message>() }
    var prompt by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val model = window.ai()
    var session: ChromeAiSession? = null

    LaunchedEffect(Unit) {
        session = model.createTextSession().await()
    }

    fun onPromptSubmit() {
        messages.add(Message.User(prompt))
        scope.launch {
            if (session == null) {
                messages.add(Message.AI.Text("Error: Session not initialized"))
                return@launch
            }
            val incomingStream = session!!.promptStreaming(prompt).getReader()
            messages.add(Message.AI.Stream(incomingStream))
            // reset prompt
            prompt = ""
        }
    }

    PageLayout("Gemini Nano") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .flex(1)
                .overflow(
                    overflowY = Overflow.Auto,
                    overflowX = Overflow.Hidden
                )
                .backgroundColor(Colors.SECONDARY)
                .padding(16.px),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SimpleGrid(
                numColumns = numColumns(1),
                modifier = ChatGridStyle.toModifier()
            ) {
                ChatMessages(messages)
            }
        }

        Box(
            modifier = PromptContainerStyle.toModifier(),
        ) {
            Row(
                modifier = PromptRowStyle.toModifier(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.px),
            ) {
                TextArea(
                    value = prompt,
                    attrs = TextAreaStyle.toAttrs {
                        onInput {
                            prompt = it.value
                        }
                        placeholder("Type a message...")
                    }
                )
                Button(
                    modifier = SendButtonStyle.toModifier(),
                    onClick = { onPromptSubmit() }
                ) {
                    FaPaperPlane(
                        size = IconSize.LG
                    )
                }
            }
        }
    }
}

val ChatGridStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .columnGap(16.px)
            .padding(leftRight = 8.px)
    }
    Breakpoint.MD {
        Modifier.padding(leftRight = 20.vw)
    }
}

val PromptContainerStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .backgroundColor(Color.white)
        .position(Position.Sticky)
        .bottom(0.px)
        .padding(topBottom = 12.px)
        .borderTop {
            width(1.px)
            style(lineStyle = LineStyle.Solid)
            color(Colors.BORDER)
        }
}

val PromptRowStyle = CssStyle {
    base {
        Modifier
            .fillMaxWidth()
            .padding(leftRight = 8.px)
    }
    Breakpoint.MD {
        Modifier
            .padding(leftRight = 20.vw)
    }
}

val TextAreaStyle = CssStyle.base {
    Modifier
        .fillMaxWidth()
        .minHeight(80.px)
        .display(DisplayStyle.Flex)
        .border { width(1.px) }
        .borderRadius(0.5.cssRem)
        .padding(
            left = 0.75.cssRem,
            right = 3.75.cssRem,
            top = 0.5.cssRem,
            bottom = 0.5.cssRem
        )
        .fontSize(0.875.cssRem)
        .lineHeight(1.25.cssRem)
        .resize(Resize.None)
}

val SendButtonStyle = CssStyle {
    base {
        Modifier
            .backgroundColor(Colors.PRIMARY)
            .color(Color.white)
            .display(DisplayStyle.LegacyInlineFlex)
            .justifyContent(JustifyContent.Center)
            .alignItems(AlignItems.Center)
            .borderRadius(12.px)
            .size(3.5.cssRem)
            .transition(
                Transition.Companion.of(
                    property = TransitionProperty.of("color,background-color,border-color,text-decoration-color,fill,stroke"),
                    duration = 300.ms,
                    timingFunction = TransitionTimingFunction.cubicBezier(0.4, 0.0, 0.2, 1.0)
                )
            )
    }
    hover {
        Modifier.backgroundColor(hsl(240, 5.9, 30))
    }
}