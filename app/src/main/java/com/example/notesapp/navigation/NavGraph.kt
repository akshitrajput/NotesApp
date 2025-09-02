package com.example.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.data.Category
import com.example.notesapp.data.Note
import com.example.notesapp.ui.screens.CategoryScreen
import com.example.notesapp.ui.screens.HomeScreen
import com.example.notesapp.ui.screens.NoteScreen
import com.example.notesapp.ui.screens.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    val notes = remember { mutableListOf<Note>() }
    val categories = remember { mutableListOf<Category>() }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                notes = notes,
                categories = categories,
                onAddCategory = { categories.add(it) }
            )
        }
        composable(
            route = Screen.Note.route,
            arguments = listOf(
                navArgument("noteId") { type = NavType.StringType; nullable = true },
                navArgument("categoryId") { type = NavType.StringType; nullable = true }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            NoteScreen(
                navController = navController,
                notes = notes,
                noteId = noteId,
                categoryId = categoryId
            )
        }
        composable(
            route = Screen.Category.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            val category = categories.find { it.id == categoryId }
            val categoryNotes = notes.filter { it.categoryId == categoryId }
            if (category != null) {
                CategoryScreen(
                    navController = navController,
                    category = category,
                    notes = categoryNotes
                )
            }
        }
    }
}
