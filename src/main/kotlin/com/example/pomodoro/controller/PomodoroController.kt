package com.example.pomodoro.controller

import com.example.pomodoro.domain.PomodoroState
import com.example.pomodoro.service.PomodoroService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/pomodoro")
class PomodoroController(private val service: PomodoroService) {

    data class StartResponse(val id: UUID, val message: String)
    data class RemainingResponse(val id: UUID, val remainingSeconds: Int)
    data class StateResponse(val id: UUID, val state: String)
    data class StopResponse(val id: UUID, val message: String)
    data class ResetResponse(val id: UUID, val message: String)
    data class HistoryResponse(val sessions: List<Map<String, Any>>)
    data class StatusResponse(val sessions: List<Map<String, Any>>)

    @PostMapping("/start")
    fun start(@RequestParam minutes: Int, @RequestParam(required = false) id: UUID?): StartResponse {
        val sessionId = id ?: UUID.randomUUID()
        service.start(minutes, sessionId)
        return StartResponse(
            id = sessionId,
            message = "Sesión $sessionId iniciada por $minutes minutos"
        )
    }

    @GetMapping("/remaining")
    fun remaining(@RequestParam id: UUID): RemainingResponse =
        RemainingResponse(id = id, remainingSeconds = service.remaining(id))

    @GetMapping("/state")
    fun state(@RequestParam id: UUID): StateResponse {
        val state = service.currentState(id)
        val description = when (state) {
            is PomodoroState.Idle -> "En espera"
            is PomodoroState.Running -> "En ejecución"
            is PomodoroState.Finished -> "Finalizado"
            is PomodoroState.Error -> state.message
        }
        return StateResponse(id = id, state = description)
    }

    @PostMapping("/stop")
    fun stop(@RequestParam id: UUID): StopResponse {
        service.stop(id)
        return StopResponse(id = id, message = "Sesión $id detenida manualmente.")
    }

    @PostMapping("/reset")
    fun reset(@RequestParam id: UUID): ResetResponse {
        service.reset(id)
        return ResetResponse(id = id, message = "Sesión $id reseteada.")
    }

    @GetMapping("/history")
    fun history(): HistoryResponse =
        HistoryResponse(
            sessions = service.history().map {
                mapOf(
                    "id" to it.id.toString(),
                    "durationMin" to it.durationMin,
                    "startTime" to it.startTime.toString(),
                    "endTime" to it.endTime.toString(),
                    "state" to it.state.javaClass.simpleName
                )
            }
        )

    @GetMapping("/all-status")
    fun allStatus(): StatusResponse =
        StatusResponse(
            sessions = service.allSessionsStatus().map { (id, state) ->
                mapOf(
                    "id" to id.toString(),
                    "state" to when (state) {
                        is PomodoroState.Idle -> "En espera"
                        is PomodoroState.Running -> "En ejecución"
                        is PomodoroState.Finished -> "Finalizado"
                        is PomodoroState.Error -> state.message
                    },
                    "remainingSeconds" to service.remaining(id)
                )
            }
        )
}

