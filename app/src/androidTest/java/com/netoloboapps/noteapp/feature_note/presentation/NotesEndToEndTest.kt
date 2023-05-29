package com.netoloboapps.noteapp.feature_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.netoloboapps.noteapp.core.util.TestTags.CONTENT_TEXT_FIELD
import com.netoloboapps.noteapp.core.util.TestTags.NOTE_ITEM
import com.netoloboapps.noteapp.core.util.TestTags.TITLE_TEXT_FIELD
import com.netoloboapps.noteapp.di.AppModule
import com.netoloboapps.noteapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.netoloboapps.noteapp.feature_note.presentation.notes.NotesScreen
import com.netoloboapps.noteapp.feature_note.presentation.util.Screen
import com.netoloboapps.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            NoteAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                    composable(route = Screen.AddEditNoteScreen.route +
                            "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(
                                name = "noteColor"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(
                            navController = navController,
                            noteColor = color
                        )
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        //Click on FAB to get to add a note screen
        composeRule
            .onNodeWithContentDescription("Add note")
            .performClick()

        //Enter texts in title and content TextFields
        composeRule
            .onNodeWithTag(TITLE_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule
            .onNodeWithTag(CONTENT_TEXT_FIELD)
            .performTextInput("test-content")
        //save the note
        composeRule
            .onNodeWithContentDescription("Save note")
            .performClick()

        //Make sure there is a note in the list with title and content
        composeRule
            .onNodeWithText("test-title")
            .assertIsDisplayed()
        //Click on the note to edit it
        composeRule
            .onNodeWithText("test-title")
            .performClick()

        //Make sure title and content TextField contains note title and content
        composeRule
            .onNodeWithTag(TITLE_TEXT_FIELD)
            .assertTextEquals("test-title")
        composeRule
            .onNodeWithTag(CONTENT_TEXT_FIELD)
            .assertTextEquals("test-content")
        //add the text 2 to to title TextField
        composeRule
            .onNodeWithTag(TITLE_TEXT_FIELD)
            .performTextInput("2")
        //update the note
        composeRule
            .onNodeWithContentDescription("Save note")
            .performClick()

        //Make sure the update was aplied to the list
        composeRule
            .onNodeWithText("test-title2")
            .assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        //Save 3 notes
        for (i in 0..3) {
            //Click on FAB to get to add a note screen
            composeRule
                .onNodeWithContentDescription("Add note")
                .performClick()

            //Enter texts in title and content TextFields
            composeRule
                .onNodeWithTag(TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule
                .onNodeWithTag(CONTENT_TEXT_FIELD)
                .performTextInput(i.toString())
            //save the note
            composeRule
                .onNodeWithContentDescription("Save note")
                .performClick()

        }

        //Make sure the 3 note is being diplayed on the screen
        composeRule
            .onNodeWithText("1")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("2")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("3")
            .assertIsDisplayed()

        //Click on Sort button
        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()
        //Click on title radio button
        composeRule
            .onNodeWithContentDescription("Title")
            .performClick()
        //Click on descending radio button
        composeRule
            .onNodeWithContentDescription("Descending")
            .performClick()

        //Make sure the notes are ordered in a descending order
        composeRule
            .onAllNodesWithTag(NOTE_ITEM)[0]
            .assertTextContains("3")
        composeRule
            .onAllNodesWithTag(NOTE_ITEM)[1]
            .assertTextContains("2")
        composeRule
            .onAllNodesWithTag(NOTE_ITEM)[2]
            .assertTextContains("1")

    }
}