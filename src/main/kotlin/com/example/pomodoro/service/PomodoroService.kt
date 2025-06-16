package com.example.pomodoro.service

import com.example.pomodoro.domain.PomodoroTimer
import com.example.pomodoro.util.formatTime
import org.springframework.stereotype.Service

@Service
class PomodoroService {
    data class RemainingTimeResponse(
        val minutesRemaining: Int,
        val formattedRemainingTime: String
    )

    fun getRemainingTime(durationSeconds: Int, currentMinutes: Int): RemainingTimeResponse {
        val timer = PomodoroTimer(durationSeconds)
        timer.start()
        val minutesRemaining = timer.remainingTime(currentMinutes)

        val formatted = formatTime(minutesRemaining * 60)
        return RemainingTimeResponse(
            minutesRemaining = minutesRemaining,
            formattedRemainingTime = formatted
        )
    }
}