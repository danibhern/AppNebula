package com.example.appnebula

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.appnebula.data.local.AppDatabase
import com.example.appnebula.data.repository.UserRepository
import com.example.appnebula.ui.UserForm
import com.example.appnebula.ui.theme.AppNebulaTheme
import com.example.appnebula.viewmodel.UserViewModel
import com.example.appnebula.viewmodel.UserViewModelFactory



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my_database"

        ).build()
        val repo = UserRepository(db.userDao())
        val factory = UserViewModelFactory(repo)
        val viewModel : UserViewModel  by viewModels { factory  }
        setContent {
            AppNebulaTheme {
                Surface (modifier = Modifier.fillMaxSize()) {
                    UserForm(viewModel)
                }
            }
        }
    }
}
