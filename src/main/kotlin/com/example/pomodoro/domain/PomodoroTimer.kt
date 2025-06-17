package com.example.pomodoro.domain

import com.example.pomodoro.util.logger
import kotlin.concurrent.thread

class PomodoroTimer(initialMinutes: Int) {
    private val log = logger()
    var remainingSeconds: Int = initialMinutes * 60
        private set
    private var onTick: ((Int) -> Unit)? = null
    private var onFinish: (() -> Unit)? = null
    fun onTick(listener: (Int) -> Unit) {
        onTick = listener
    }

    fun onFinish(listener: () -> Unit) {
        onFinish = listener
    }

    fun start() {
        thread(name = "PomodoroTimer") { // detalle mínimo de concurrencia
            while (remainingSeconds > 0) {
                Thread.sleep(1_000)
                remainingSeconds--
                onTick?.invoke(remainingSeconds)
            }
            onFinish?.invoke()
            log.info { "Pomodoro finalizado" }
        }
    }
}
