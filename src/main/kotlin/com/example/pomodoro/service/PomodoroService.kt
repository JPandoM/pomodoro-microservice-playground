package com.example.pomodoro.service

import com.example.pomodoro.domain.PomodoroState
import com.example.pomodoro.domain.PomodoroTimer
import com.example.pomodoro.util.logger
import com.example.pomodoro.util.validateMinutes
import org.springframework.stereotype.Service

@Service
class PomodoroService {

    private val log = logger()
    private var timer: PomodoroTimer? = null      // sencillo: solo un temporizador activo
    private var state: PomodoroState = PomodoroState.Idle
    fun start(minutes: Int) {
        val validMinutes = validateMinutes(minutes)

        timer = PomodoroTimer(validMinutes).apply {
            onTick { sec ->
                log.info { "Tick: $sec s restantes" }
                state = PomodoroState.Running
            }
            onFinish {
                log.info {
                    "¡Sesión terminada! Hora del descanso"
                }
                state = PomodoroState.Finished
            }
            start()

        }
        state = PomodoroState.Running
    }

    fun currentState(): PomodoroState = state

    fun remaining(): Int = timer?.remainingSeconds ?: 0

    fun reset() {
        timer?.reset()
        state = PomodoroState.Idle
        log.info { "Pomodoro reseteado" }
    }

    fun stop() {
        timer = null
        state = PomodoroState.Idle
        log.info { "Pomodoro detenido" }
    }
}