package com.example.pomodoro.controller
import com.example.pomodoro.service.PomodoroService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
@RestController
class PomodoroController(
    private val pomodoroService: PomodoroService
) {
    @GetMapping("/pomodoro/remaining-time")
    fun getRemainingTime(
        @RequestParam durationSeconds: Int,
        @RequestParam currentMinutes: Int
    ): PomodoroService.RemainingTimeResponse =
        pomodoroService.getRemainingTime(durationSeconds, currentMinutes)
}