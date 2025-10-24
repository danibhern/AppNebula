package com.example.appnebula.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UsuarioErrores(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
)

data class UsuarioEstado(
    val name: String = "",
    val email: String = "",
    val password: String = "",

    val errores: UsuarioErrores = UsuarioErrores()
)

class UserViewModel : ViewModel() {

    private val _estado = MutableStateFlow(UsuarioEstado())
    val estado = _estado.asStateFlow()

    fun onNombreChange(value: String) = _estado.update { it.copy(name = value) }
    fun onCorreoChange(value: String) = _estado.update { it.copy(email = value) }
    fun onClaveChange(value: String) = _estado.update { it.copy(password = value) }


    // ✅ Validación global del formulario
    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value

        val errores = UsuarioErrores(
            name = if (estadoActual.name.isBlank()) "Campo obligatorio" else null,
            email = if (!estadoActual.email.contains("@")) "Correo inválido" else null,
            password = if (estadoActual.password.length < 6) "Debe tener al menos 6 caracteres" else null,
        )

        val hayErrores = listOfNotNull(
            errores.name,
            errores.email,
            errores.password
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    // ✅ Guardar solo si pasa la validación
    fun guardarUsuario() {
        if (validarFormulario()) {
            // Aquí puedes insertar en SQLite (Room)
            // repository.insert(User(...))
        }
    }
}
