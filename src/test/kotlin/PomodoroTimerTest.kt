import com.example.pomodoro.domain.PomodoroTimer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PomodoroTimerTest {
    @Test
    fun `remainingTime should calculate correct minutes`() {
        val timer = PomodoroTimer(durationSeconds = 1500)
        val remaining = timer.remainingTime(currentMinutes = 5)
        assertEquals(20, remaining)
    }

    @Test
    fun `start should set isRunning to true`() {
        val timer = PomodoroTimer(durationSeconds = 1500)
        timer.start()
        assertTrue(timer.isRunning)
    }

    @Test
    fun `pause should set isRunning to false`() {
        val timer = PomodoroTimer(durationSeconds = 1500)
        timer.start()
        timer.pause()
        assertFalse(timer.isRunning)
    }

    @Test
    fun `reset should set remainingSeconds to initial duration`() {
        val timer = PomodoroTimer(durationSeconds = 1500)
        timer.start()
        timer.reset()
        assertEquals(1500, timer.remainingSeconds)
        assertFalse(timer.isRunning)
    }
}