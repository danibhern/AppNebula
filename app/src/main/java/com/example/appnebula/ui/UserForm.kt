package com.example.appnebula.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appnebula.viewmodel.UserViewModel

@Composable
fun UserForm(viewModel: UserViewModel) {
    val estado = viewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Registro de Usuario",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        // Nombre
        OutlinedTextField(
            value = estado.value.name,
            onValueChange = viewModel::onNombreChange,
            label = { Text("Nombre") },
            isError = estado.value.errores.name != null,
            supportingText = {
                estado.value.errores.name?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Email
        OutlinedTextField(
            value = estado.value.email,
            onValueChange = viewModel::onCorreoChange,
            label = { Text("Email") },
            isError = estado.value.errores.email != null,
            supportingText = {
                estado.value.errores.email?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Contraseña
        OutlinedTextField(
            value = estado.value.password,
            onValueChange = viewModel::onClaveChange,
            label = { Text("Contraseña") },
            isError = estado.value.errores.password != null,
            supportingText = {
                estado.value.errores.password?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Botón Guardar
        Button(
            onClick = { viewModel.guardarUsuario() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar", color = Color.White)
        }
    }
}
