package com.mosiuk.funnycombination

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mosiuk.funnycombination.ui.navigation.AppNavGraph
import com.mosiuk.funnycombination.ui.theme.FunnyCombinationTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Главная и единственная Activity в приложении.
 * Аннотация @AndroidEntryPoint позволяет внедрять зависимости в Activity.
 * Является хостом для NavHost, который управляет навигацией между экранами.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunnyCombinationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavGraph(navController = navController)
                }
            }
        }
    }
}