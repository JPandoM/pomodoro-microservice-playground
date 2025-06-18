package com.example.sandbox

data class Usuario(val nombre: String, val edad: Int)

fun main() {
    // Creación de la instancia de la data class Usuario
    val user1 = Usuario("Ana", 25)
    println("=== Creación de Objetos ===")
    println("User1: $user1")
    println()

    // Crear una copia modificando la edad
    val user2 = user1.copy(edad = 26)
    println("Después de modificar la edad con copy():")
    println("User2: $user2")
    println()

    // Crear una copia sin cambios
    val user3 = user1.copy()
    println("Copia sin cambios:")
    println("User3: $user3")
    println()

    // Comparaciones entre objetos
    println("=== Comparaciones ===")
    println("User1 == User2: ${user1 == user2}") // false, porque la edad difiere
    println("User1 == User3: ${user1 == user3} (${if (user1 == user3) "1 y 3 son iguales" else "1 y 3 son distintos"})")
    println("user1.equals(user2): ${user1.equals(user2)}")
    println()

    // Mostrar hashCodes de cada objeto
    println("=== Códigos Hash ===")
    println("user1 hashCode: ${user1.hashCode()}")
    println("user2 hashCode: ${user2.hashCode()}") // Distinto del de user1
    println("user3 hashCode: ${user3.hashCode()}") // Igual al de user1
    println()

    // Desestructuración: extraer las propiedades de cada usuario
    println("=== Desestructuración de Propiedades ===")
    println("--- val (nombre*, edad*) = user*")
    val (nombre1, edad1) = user1
    println("Desde user1 -> Nombre: $nombre1, Edad: $edad1")

    val (nombre2, edad2) = user2
    println("Desde user2 -> Nombre: $nombre2, Edad: $edad2")

    val (nombre3, edad3) = user3
    println("Desde user3 -> Nombre: $nombre3, Edad: $edad3")
}