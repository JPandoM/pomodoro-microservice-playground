package com.example.pomodoro.service

import com.example.pomodoro.domain.PomodoroSession
import com.example.pomodoro.domain.PomodoroState
import com.example.pomodoro.domain.PomodoroTimer
import com.example.pomodoro.util.logger
import com.example.pomodoro.util.validateMinutes
import org.springframework.stereotype.Service
import java.util.*

@Service
class PomodoroService {

    private val log = logger()

    // Sesiones activas y finalizadas
    private val activeTimers = mutableMapOf<UUID, PomodoroTimer>()
    private val finishedSessions = mutableListOf<PomodoroSession>()
    private val sessionStates = mutableMapOf<UUID, PomodoroState>()

    // Mantener método para obtener estado actual por id
    fun currentState(id: UUID): PomodoroState =
        sessionStates[id] ?: PomodoroState.Error("No encontrada")

    fun start(minutes: Int, sessionId: UUID = UUID.randomUUID()): UUID {
        val validMinutes = validateMinutes(minutes)
        val startTime = java.time.Instant.now()
        sessionStates[sessionId] = PomodoroState.Running
        val timer = PomodoroTimer(validMinutes).apply {
            onFinish {
                val session =
                    PomodoroSession(sessionId, validMinutes, startTime, java.time.Instant.now(), PomodoroState.Finished)
                finishedSessions += session
                activeTimers.remove(sessionId)
                log.info { "¡Sesión $sessionId terminada!" }
                sessionStates[sessionId] = PomodoroState.Finished
            }
            start()
        }
        activeTimers[sessionId] = timer.also { log.info { "Temporizador $sessionId iniciado" } }
        return sessionId
    }

    fun remaining(id: UUID): Int =
        activeTimers[id]?.remainingSeconds ?: 0

    fun stop(id: UUID) {
        activeTimers[id]?.let { timer ->
            val session = PomodoroSession(
                id,
                timer.remainingSeconds / 60,
                java.time.Instant.now(),
                java.time.Instant.now(),
                PomodoroState.Finished
            )
            finishedSessions += session
            activeTimers.remove(id)
            sessionStates[id] = PomodoroState.Finished
        }
    }

    fun reset(id: UUID) {
        activeTimers[id]?.reset()
        sessionStates[id] = PomodoroState.Idle
        // No ha falta pero por semántica, se para y reinicia
        sessionStates[id] = PomodoroState.Running
        log.info { "Sesión $id reseteada" }
    }

    fun allSessionsStatus(): List<Pair<UUID, PomodoroState>> {
        // Incluye las activas y las ya finalizadas (usa el estado más reciente)
        val activeIds = activeTimers.keys
        val allIds = activeIds + finishedSessions.map { it.id }
        return allIds.distinct().map { id ->
            id to (sessionStates[id] ?: PomodoroState.Error("No encontrada"))
        }
    }

    fun history(): List<PomodoroSession> = finishedSessions.toList()
}