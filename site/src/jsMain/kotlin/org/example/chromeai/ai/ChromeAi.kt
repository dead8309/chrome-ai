@file:Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE", "UNUSED")

package org.example.chromeai.ai

import js.promise.Promise
import org.w3c.dom.Window
import web.streams.ReadableStream

sealed interface ChromeAiSessionAvailable {
    @JsName("no")
    val no: Boolean
    @JsName("readily")
    val readily: Boolean
}

data class ChromeAiSessionOptions(
    @JsName("temperature")
    val temperature: Double? = null,
    @JsName("topK")
    val topK: Int? = null
)

external interface ChromeAiSession {
    fun destroy(): Promise<Unit>
    fun prompt(prompt: String): Promise<String>
    fun promptStreaming(prompt: String): ReadableStream<String>
    fun execute(prompt: String): Promise<String>
    fun executeStreaming(prompt: String): ReadableStream<String>
}

external interface ChromeAiAPI {
    fun canCreateGenericSession(): Promise<ChromeAiSessionAvailable>
    fun canCreateTextSession(): Promise<ChromeAiSessionAvailable>
    fun defaultGenericSessionOptions(): Promise<ChromeAiSessionOptions>
    fun defaultTextSessionOptions(): Promise<ChromeAiSessionOptions>
    fun createGenericSession(options: ChromeAiSessionOptions? = definedExternally): Promise<ChromeAiSession>
    fun createTextSession(options: ChromeAiSessionOptions? = definedExternally): Promise<ChromeAiSession>
}

fun Window.ai(): ChromeAiAPI {
    return asDynamic().ai as ChromeAiAPI
}