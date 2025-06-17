package com.example.pomodoro.service

import com.example.pomodoro.domain.PomodoroTimer
import com.example.pomodoro.util.logger
import com.example.pomodoro.util.validateMinutes
import org.springframework.stereotype.Service

@Service
class PomodoroService {
    private val log = logger()
    private var timer: PomodoroTimer? = null // sencillo: solo un temporizador activo
    fun start(minutes: Int) {
        val validMinutes = validateMinutes(minutes)
        timer = PomodoroTimer(validMinutes).apply {
            onTick { sec -> log.info { "Tick: $sec s restantes" } }
            onFinish { log.info { "¡Sesión terminada! Hora del descanso" } }
            start()
        }
    }

    fun remaining(): Int = timer?.remainingSeconds ?: 0
}
