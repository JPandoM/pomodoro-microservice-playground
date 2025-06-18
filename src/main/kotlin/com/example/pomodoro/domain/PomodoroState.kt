package com.example.pomodoro.domain

sealed class PomodoroState {
    object Idle : PomodoroState() // No iniciado
    object Running : PomodoroState() // En ejecución
    object Finished : PomodoroState() // Finalizado
    data class Error(val message: String) : PomodoroState() // Estado de error, con mensaje
}