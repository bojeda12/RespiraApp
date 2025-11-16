package com.example.examplemvvm.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examplemvvm.data.local.entities.RegistroEstadoAnimoEntity
import com.example.examplemvvm.domain.model.EstadoFrecuenteDia

@Dao
interface RegistroEstadoAnimoDao {
    //@Query("SELECT * FROM registroestadoanimo WHERE id_usuario = :usuarioId")
    //suspend fun getRegistrosByUsuario(usuarioId: Int): List<RegistroEstadoAnimoEntity>
    @Query("SELECT * FROM registroestadoanimo WHERE id_usuario = :idUsuario ORDER BY id DESC LIMIT 1")
    suspend fun obtenerUltimoEstado(idUsuario: Int): RegistroEstadoAnimoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEstado(estado: RegistroEstadoAnimoEntity)

    @Query("""
    SELECT diaSemana, estadoAnimo
    FROM (
        SELECT 
            strftime('%w', fecha) AS diaSemana,
            estadoAnimo,
            COUNT(*) AS frecuencia
        FROM RegistroEstadoAnimo
        WHERE id_usuario = :idUsuario
          AND estadoAnimo IN ('1','2','3','4','5')
          AND fecha BETWEEN :inicioSemana AND :finSemana
        GROUP BY diaSemana, estadoAnimo
    )
    GROUP BY diaSemana
    HAVING MAX(frecuencia)
""")
    suspend fun obtenerEstadoAnimoMasFrecuentePorDia(
        idUsuario: Int,
        inicioSemana: String,
        finSemana: String
    ): List<EstadoFrecuenteDia>





}