package com.example.pomodoro.controller

import com.example.pomodoro.service.PomodoroService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pomodoro")
class PomodoroController(private val service: PomodoroService) {
    /** Inicia una sesión de X minutos (1-60) */
    @PostMapping("/start")
    fun start(@RequestParam minutes: Int) = service.start(minutes).let { "Temporizador iniciado por $minutes minutos" }

    /** Devuelve los segundos restantes */
    @GetMapping("/remaining")
    fun remaining(): Int = service.remaining()
}
