package com.example.pomodoro.util

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging

inline fun <reified T> T.logger(): KLogger = KotlinLogging.logger(T::class.java.name)

fun fail(message: String): Nothing {
    throw IllegalStateException(message)
}