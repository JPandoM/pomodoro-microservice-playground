package com.example.pomodoro.domain

import java.time.Instant
import java.util.UUID

data class PomodoroSession(
    val id: UUID,
    val durationMin: Int,
    val startTime: Instant,
    val endTime: Instant,
    val state: PomodoroState
) {
    // Suma las duraciones y toma el estado del último
    operator fun plus(other: PomodoroSession): PomodoroSession =
        PomodoroSession(
            UUID.randomUUID(),
            this.durationMin + other.durationMin,
            this.startTime,
            other.endTime,
            other.state
        )

    // Igualdad por id
    override fun equals(other: Any?): Boolean =
        other is PomodoroSession && this.id == other.id

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String =
        "Sesión [$id]: $durationMin min (${state.javaClass.simpleName}) finalizada en $endTime"
}