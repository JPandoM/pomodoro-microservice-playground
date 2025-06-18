package com.example.pomodoro.controller

import com.example.pomodoro.domain.PomodoroState
import com.example.pomodoro.service.PomodoroService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pomodoro")
class PomodoroController(private val service: PomodoroService) {
    /** Inicia una sesión de X minutos (1-60) */
    @PostMapping("/start")
    fun start(@RequestParam minutes: Int) =
        service.start(minutes).let { "Temporizador iniciado por $minutes minutos" }

    @PostMapping("/reset")
    fun reset(): String {
        service.reset()
        return "Pomodoro reseteado"
    }
    @PostMapping("/stop")
    fun stop(): String {
        service.stop()
        return "Pomodoro detenido"
    }

    /** Devuelve los segundos restantes */
    @GetMapping("/remaining")
    fun remaining(): Int = service.remaining()

    @GetMapping("/state")
    fun state(): Map<String, String> {
        val state = service.currentState()
        // Ejemplo de uso de when + fail + safe call + Elvis
        val description = when (state) {
            is PomodoroState.Idle -> "En espera"
            is PomodoroState.Running -> "En ejecución"
            is PomodoroState.Finished -> "Finalizado"
            is PomodoroState.Error -> state.message
            // else -> fail("Estado no conocido") // no hace falta ya que when es exhaustivo gracias a sealed class
        }
        return mapOf("state" to description)
    }
}
