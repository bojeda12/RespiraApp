package com.example.examplemvvm.data.remote.api

import com.example.examplemvvm.data.remote.dto.SesionDTO
import com.example.examplemvvm.data.remote.response.HorarioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("predecir")
    suspend fun enviarSesiones(@Body sesiones: List<SesionDTO>): Response<HorarioResponse>

    @POST("estado")
    suspend fun predecirEstado(@Body sesion: SesionDTO): Response<Map<String, Any>>
}