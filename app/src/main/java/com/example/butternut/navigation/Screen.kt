package com.example.butternut.navigation

sealed class Screen(val route: String){
    object HomeScreen : Screen("home")
    object ClassScreen : Screen("class")
    object DeckScreen : Screen("deck")
    object FlashCardScreen : Screen("flash_card")
    object PlayScreen : Screen("play")
    object AddNewClassScreen : Screen("add_new_class")
    object AddNewDeckScreen : Screen("add_new_deck")
    object AddOrUpdateFlashCardScreen : Screen("add_or_update_flash_card")
}
