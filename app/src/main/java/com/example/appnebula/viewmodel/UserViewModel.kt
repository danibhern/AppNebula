package com.example.appnebula.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appnebula.data.local.User
import com.example.appnebula.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _estado = MutableStateFlow(UsuarioEstado())
    val estado = _estado.asStateFlow()

    // Cambios de los campos
    fun onNombreChange(value: String) = _estado.update { it.copy(name = value) }
    fun onCorreoChange(value: String) = _estado.update { it.copy(email = value) }
    fun onClaveChange(value: String) = _estado.update { it.copy(password = value) }

    // Validación del formulario
    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value

        val errores = UsuarioErrores(
            name = if (estadoActual.name.isBlank()) "Campo obligatorio" else null,
            email = if (!estadoActual.email.contains("@")) "Correo inválido" else null,
            password = if (estadoActual.password.length < 6) "Debe tener al menos 6 caracteres" else null,
        )

        val hayErrores = listOfNotNull(errores.name, errores.email, errores.password).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    // Guardar en Room solo si pasa validación
    fun guardarUsuario() {
        if (validarFormulario()) {
            viewModelScope.launch {
                repository.insert(
                    User(
                        name = _estado.value.name,
                        email = _estado.value.email,
                        password = _estado.value.password
                    )
                )
            }
        }
    }
}
