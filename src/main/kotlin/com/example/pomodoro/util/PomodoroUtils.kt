package com.example.pomodoro.util

/**
 * Valida la duración en minutos (1-60) y la devuelve.
 * - Lanza IllegalArgumentException si no cumple el rango.
 * - Es una lambda reutilizable para mantener el tema de “funciones como valores”.
 */
val validateMinutes: (Int) -> Int = { minutes ->
    require(minutes in 1..60) { "La duración debe estar entre 1 y 60 minutos" }
    minutes
}
