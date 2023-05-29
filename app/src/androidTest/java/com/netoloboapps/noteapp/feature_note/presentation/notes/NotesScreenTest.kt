package com.netoloboapps.noteapp.feature_note.presentation.notes

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.netoloboapps.noteapp.core.util.TestTags.ORDER_SECTION
import com.netoloboapps.noteapp.di.AppModule
import com.netoloboapps.noteapp.feature_note.presentation.MainActivity
import com.netoloboapps.noteapp.feature_note.presentation.util.Screen
import com.netoloboapps.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before

import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            NoteAppTheme {
                NavHost(
                    navController = navController, 
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        composeRule.onNodeWithTag(ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(ORDER_SECTION).assertIsDisplayed()

    }

}