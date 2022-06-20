package com.apilaravel

import java.io.Serializable

data class CancionData(
    val id: Int? = 0,
    var archivo: String? = "",
    var artista: String? = "",
    var titulo: String? = "",
    var album: String? = "",
    var year: String? = "",
    var formato: String? = "",
    var genero: String? = "",
    var image: String? = "",
    var duracion: String? = "",
    var numero_reproduccion: String? = "",
    var created_at: String? = "",
    var updated_at: String? = "",
): Serializable
