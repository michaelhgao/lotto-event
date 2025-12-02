package com.example.lottoevent.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lottoevent.singletons.UserEventStore
import com.example.lottoevent.ui.theme.LottoEventTheme

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
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Create Event Screen", fontSize = 24.sp)
        Button(onClick = { navController.popBackStack() }) {
            Text("Go Back")
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