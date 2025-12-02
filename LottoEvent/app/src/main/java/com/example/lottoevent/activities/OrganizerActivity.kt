package com.example.lottoevent.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lottoevent.models.Event
import com.example.lottoevent.models.WaitingList
import com.example.lottoevent.singletons.FirebaseManager
import com.example.lottoevent.singletons.UserEventStore
import com.example.lottoevent.ui.theme.LottoEventTheme
import kotlinx.coroutines.launch

object OrganizerDestinations {
    const val HOME = "organizer_home"
    const val CREATE_EVENT = "create_event"
    const val VIEW_EVENTS = "view_events"
}

class OrganizerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = UserEventStore.getUser();
        val userName = user?.name ?: "Organizer"
        setContent {
            LottoEventTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   OrganizerApp(userName);
                }
            }
        }
    }
}

@Composable
fun OrganizerApp(name: String) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = OrganizerDestinations.HOME
    ) {
        composable(OrganizerDestinations.HOME) {
            OrganizerActivityLayout(
                name = name,
                navController = navController
            )
        }
        composable(OrganizerDestinations.CREATE_EVENT) {
            CreateEventScreen(navController)
        }
        composable(OrganizerDestinations.VIEW_EVENTS) {
            ViewEventsScreen(navController)
        }
    }
}

@Composable
fun OrganizerActivityLayout(name: String, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Welcome Text
        Text(
            text = "Welcome, $name",
            fontSize = 28.sp,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Create Event Button
        Button(
            onClick = {
                navController.navigate(OrganizerDestinations.CREATE_EVENT);
            },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("Create Event")
        }

        // View Events Button
        Button(
            onClick = {
                navController.navigate(OrganizerDestinations.VIEW_EVENTS)
            }
        ) {
            Text("View Events")
        }
    }
}

@Composable
fun CreateEventScreen(navController: NavController) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var capacityText by remember { mutableStateOf(TextFieldValue("")) }
    var isLoading by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val organizer = UserEventStore.getUser()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
        ) {
            Text(
                text = "Create Event",
                fontSize = 32.sp,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Event Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Event Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )
            OutlinedTextField(
                value = capacityText,
                onValueChange = { newValue ->
                    if (newValue.text.all { it.isDigit() } || newValue.text == "-" || (newValue.text.startsWith("-") && newValue.text.length == 1)) {
                        capacityText = newValue
                    } else if (newValue.text.startsWith("-")) {
                        capacityText = newValue.copy(text = newValue.text.filterIndexed { index, c -> index == 0 || c.isDigit() })
                    } else {
                        capacityText = newValue.copy(text = newValue.text.filter { it.isDigit() })
                    }
                },
                label = { Text("Capacity (e.g., 50 or -1 for unlimited)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Button(
                onClick = {
                    val capacityInt = capacityText.text.toIntOrNull()

                    if (title.text.isBlank() || description.text.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Title and Description cannot be empty.")
                        }
                        return@Button
                    }

                    if (capacityInt == null) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please enter a valid number for Capacity.")
                        }
                        return@Button
                    }

                    if (organizer == null) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Error: Organizer user is not set.")
                        }
                        return@Button
                    }

                    val event = Event(
                        title = title.text,
                        description = description.text,
                        capacity = capacityInt,
                        organizer = organizer,
                        waitingList = WaitingList(capacity = capacityInt)
                    );

                    isLoading = true;

                    scope.launch {
                        try {
                            val result = FirebaseManager.addEvent(event)
                            result.onSuccess {
                                snackbarHostState.showSnackbar("Event '${event.title}' Created!")
                                navController.popBackStack()
                            }.onFailure { exception ->
                                snackbarHostState.showSnackbar("Failed to create event: ${exception.localizedMessage}")
                            }
                        }
                        finally {
                            isLoading = false;
                        }

                    }
                },
                modifier = Modifier.padding(top = 16.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 3.dp
                    )
                } else {
                    Text("Create Event")
                }
            }
            Button(onClick = { navController.popBackStack() }) {
                Text("Cancel / Go Back")
            }
        }
    }
}

@Composable
fun ViewEventsScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("View Events Screen", fontSize = 24.sp)
        Button(onClick = { navController.popBackStack() }) {
            Text("Go Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrganizerActivityLayoutPreview() {
    LottoEventTheme {
        val mockNavController = rememberNavController()
        OrganizerActivityLayout("Organizer", mockNavController)
    }
}

@Preview(showBackground = true)
@Composable
fun CreateEventScreenPreview() {
    LottoEventTheme {
        val mockNavController = rememberNavController()
        CreateEventScreen(mockNavController)
    }
}

@Preview(showBackground = true)
@Composable
fun ViewEventsScreenPreview() {
    LottoEventTheme {
        val mockNavController = rememberNavController()
        ViewEventsScreen(mockNavController)
    }
}