package com.example.pomodoro.domain

import org.slf4j.LoggerFactory

class PomodoroTimer(val durationSeconds: Int) {

    private val logger = LoggerFactory.getLogger(PomodoroTimer::class.java)
    val totalMinutes: Int = durationSeconds / 60
    var remainingSeconds: Int = durationSeconds
        private set
    var isRunning: Boolean = false
        private set

    fun start() {
        isRunning = true
        logger.info("Timer started.")
    }

    fun pause() {
        isRunning = false
        logger.info("Timer paused.")
    }

    fun reset() {
        isRunning = false
        remainingSeconds = durationSeconds
        logger.info("Timer reset.")
    }

    fun remainingTime(currentMinutes: Int): Int {
        return totalMinutes - currentMinutes
    }
}