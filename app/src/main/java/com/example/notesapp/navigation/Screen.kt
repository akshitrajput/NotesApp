package com.example.notesapp.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("home_screen")
    object Note : Screen("note_screen?noteId={noteId}&categoryId={categoryId}") {
        fun createRoute(noteId: String, categoryId: String? = null): String {
            return "note_screen?noteId=$noteId" + (categoryId?.let { "&categoryId=$it" } ?: "")
        }
    }
    object Category : Screen("category_screen/{categoryId}") {
        fun createRoute(categoryId: String) = "category_screen/$categoryId"
    }
}
